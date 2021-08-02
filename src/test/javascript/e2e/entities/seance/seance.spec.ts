import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SeanceComponentsPage, SeanceDeleteDialog, SeanceUpdatePage } from './seance.page-object';

const expect = chai.expect;

describe('Seance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let seanceComponentsPage: SeanceComponentsPage;
  let seanceUpdatePage: SeanceUpdatePage;
  let seanceDeleteDialog: SeanceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Seances', async () => {
    await navBarPage.goToEntity('seance');
    seanceComponentsPage = new SeanceComponentsPage();
    await browser.wait(ec.visibilityOf(seanceComponentsPage.title), 5000);
    expect(await seanceComponentsPage.getTitle()).to.eq('autoEcoleApp.seance.home.title');
    await browser.wait(ec.or(ec.visibilityOf(seanceComponentsPage.entities), ec.visibilityOf(seanceComponentsPage.noResult)), 1000);
  });

  it('should load create Seance page', async () => {
    await seanceComponentsPage.clickOnCreateButton();
    seanceUpdatePage = new SeanceUpdatePage();
    expect(await seanceUpdatePage.getPageTitle()).to.eq('autoEcoleApp.seance.home.createOrEditLabel');
    await seanceUpdatePage.cancel();
  });

  it('should create and save Seances', async () => {
    const nbButtonsBeforeCreate = await seanceComponentsPage.countDeleteButtons();

    await seanceComponentsPage.clickOnCreateButton();

    await promise.all([
      seanceUpdatePage.setDateHeurePrevuInput('dateHeurePrevu'),
      seanceUpdatePage.setDateHeureReelInput('dateHeureReel'),
      seanceUpdatePage.setNbrHeureInput('5'),
      seanceUpdatePage.setTypeInput('type'),
      seanceUpdatePage.moniteurSelectLastOption(),
      seanceUpdatePage.candidatSelectLastOption(),
      seanceUpdatePage.vehiculeSelectLastOption()
    ]);

    expect(await seanceUpdatePage.getDateHeurePrevuInput()).to.eq(
      'dateHeurePrevu',
      'Expected DateHeurePrevu value to be equals to dateHeurePrevu'
    );
    expect(await seanceUpdatePage.getDateHeureReelInput()).to.eq(
      'dateHeureReel',
      'Expected DateHeureReel value to be equals to dateHeureReel'
    );
    expect(await seanceUpdatePage.getNbrHeureInput()).to.eq('5', 'Expected nbrHeure value to be equals to 5');
    expect(await seanceUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await seanceUpdatePage.save();
    expect(await seanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await seanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Seance', async () => {
    const nbButtonsBeforeDelete = await seanceComponentsPage.countDeleteButtons();
    await seanceComponentsPage.clickOnLastDeleteButton();

    seanceDeleteDialog = new SeanceDeleteDialog();
    expect(await seanceDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.seance.delete.question');
    await seanceDeleteDialog.clickOnConfirmButton();

    expect(await seanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
