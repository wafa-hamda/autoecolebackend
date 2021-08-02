import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EntretienComponentsPage, EntretienDeleteDialog, EntretienUpdatePage } from './entretien.page-object';

const expect = chai.expect;

describe('Entretien e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let entretienComponentsPage: EntretienComponentsPage;
  let entretienUpdatePage: EntretienUpdatePage;
  let entretienDeleteDialog: EntretienDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Entretiens', async () => {
    await navBarPage.goToEntity('entretien');
    entretienComponentsPage = new EntretienComponentsPage();
    await browser.wait(ec.visibilityOf(entretienComponentsPage.title), 5000);
    expect(await entretienComponentsPage.getTitle()).to.eq('autoEcoleApp.entretien.home.title');
    await browser.wait(ec.or(ec.visibilityOf(entretienComponentsPage.entities), ec.visibilityOf(entretienComponentsPage.noResult)), 1000);
  });

  it('should load create Entretien page', async () => {
    await entretienComponentsPage.clickOnCreateButton();
    entretienUpdatePage = new EntretienUpdatePage();
    expect(await entretienUpdatePage.getPageTitle()).to.eq('autoEcoleApp.entretien.home.createOrEditLabel');
    await entretienUpdatePage.cancel();
  });

  it('should create and save Entretiens', async () => {
    const nbButtonsBeforeCreate = await entretienComponentsPage.countDeleteButtons();

    await entretienComponentsPage.clickOnCreateButton();

    await promise.all([
      entretienUpdatePage.setLibelleInput('libelle'),
      entretienUpdatePage.setPrixInput('5'),
      entretienUpdatePage.vehiculeSelectLastOption()
    ]);

    expect(await entretienUpdatePage.getLibelleInput()).to.eq('libelle', 'Expected Libelle value to be equals to libelle');
    expect(await entretienUpdatePage.getPrixInput()).to.eq('5', 'Expected prix value to be equals to 5');

    await entretienUpdatePage.save();
    expect(await entretienUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await entretienComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Entretien', async () => {
    const nbButtonsBeforeDelete = await entretienComponentsPage.countDeleteButtons();
    await entretienComponentsPage.clickOnLastDeleteButton();

    entretienDeleteDialog = new EntretienDeleteDialog();
    expect(await entretienDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.entretien.delete.question');
    await entretienDeleteDialog.clickOnConfirmButton();

    expect(await entretienComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
