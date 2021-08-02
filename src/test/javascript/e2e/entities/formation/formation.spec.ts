import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormationComponentsPage, FormationDeleteDialog, FormationUpdatePage } from './formation.page-object';

const expect = chai.expect;

describe('Formation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formationComponentsPage: FormationComponentsPage;
  let formationUpdatePage: FormationUpdatePage;
  let formationDeleteDialog: FormationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Formations', async () => {
    await navBarPage.goToEntity('formation');
    formationComponentsPage = new FormationComponentsPage();
    await browser.wait(ec.visibilityOf(formationComponentsPage.title), 5000);
    expect(await formationComponentsPage.getTitle()).to.eq('autoEcoleApp.formation.home.title');
    await browser.wait(ec.or(ec.visibilityOf(formationComponentsPage.entities), ec.visibilityOf(formationComponentsPage.noResult)), 1000);
  });

  it('should load create Formation page', async () => {
    await formationComponentsPage.clickOnCreateButton();
    formationUpdatePage = new FormationUpdatePage();
    expect(await formationUpdatePage.getPageTitle()).to.eq('autoEcoleApp.formation.home.createOrEditLabel');
    await formationUpdatePage.cancel();
  });

  it('should create and save Formations', async () => {
    const nbButtonsBeforeCreate = await formationComponentsPage.countDeleteButtons();

    await formationComponentsPage.clickOnCreateButton();

    await promise.all([
      formationUpdatePage.setDateDebutInput('dateDebut'),
      formationUpdatePage.setDateFinInput('dateFin'),
      formationUpdatePage.setNbrHeureCodeInput('5'),
      formationUpdatePage.setPrixheureCodeInput('5'),
      formationUpdatePage.setNbrHeureConduitInput('5'),
      formationUpdatePage.setPrixHeureConduitInput('5'),
      formationUpdatePage.setDisponobilteInput('disponobilte'),
      formationUpdatePage.candidatSelectLastOption()
    ]);

    expect(await formationUpdatePage.getDateDebutInput()).to.eq('dateDebut', 'Expected DateDebut value to be equals to dateDebut');
    expect(await formationUpdatePage.getDateFinInput()).to.eq('dateFin', 'Expected DateFin value to be equals to dateFin');
    expect(await formationUpdatePage.getNbrHeureCodeInput()).to.eq('5', 'Expected nbrHeureCode value to be equals to 5');
    expect(await formationUpdatePage.getPrixheureCodeInput()).to.eq('5', 'Expected prixheureCode value to be equals to 5');
    expect(await formationUpdatePage.getNbrHeureConduitInput()).to.eq('5', 'Expected nbrHeureConduit value to be equals to 5');
    expect(await formationUpdatePage.getPrixHeureConduitInput()).to.eq('5', 'Expected prixHeureConduit value to be equals to 5');
    expect(await formationUpdatePage.getDisponobilteInput()).to.eq(
      'disponobilte',
      'Expected Disponobilte value to be equals to disponobilte'
    );

    await formationUpdatePage.save();
    expect(await formationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Formation', async () => {
    const nbButtonsBeforeDelete = await formationComponentsPage.countDeleteButtons();
    await formationComponentsPage.clickOnLastDeleteButton();

    formationDeleteDialog = new FormationDeleteDialog();
    expect(await formationDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.formation.delete.question');
    await formationDeleteDialog.clickOnConfirmButton();

    expect(await formationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
