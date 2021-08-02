import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AssuranceComponentsPage, AssuranceDeleteDialog, AssuranceUpdatePage } from './assurance.page-object';

const expect = chai.expect;

describe('Assurance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assuranceComponentsPage: AssuranceComponentsPage;
  let assuranceUpdatePage: AssuranceUpdatePage;
  let assuranceDeleteDialog: AssuranceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Assurances', async () => {
    await navBarPage.goToEntity('assurance');
    assuranceComponentsPage = new AssuranceComponentsPage();
    await browser.wait(ec.visibilityOf(assuranceComponentsPage.title), 5000);
    expect(await assuranceComponentsPage.getTitle()).to.eq('autoEcoleApp.assurance.home.title');
    await browser.wait(ec.or(ec.visibilityOf(assuranceComponentsPage.entities), ec.visibilityOf(assuranceComponentsPage.noResult)), 1000);
  });

  it('should load create Assurance page', async () => {
    await assuranceComponentsPage.clickOnCreateButton();
    assuranceUpdatePage = new AssuranceUpdatePage();
    expect(await assuranceUpdatePage.getPageTitle()).to.eq('autoEcoleApp.assurance.home.createOrEditLabel');
    await assuranceUpdatePage.cancel();
  });

  it('should create and save Assurances', async () => {
    const nbButtonsBeforeCreate = await assuranceComponentsPage.countDeleteButtons();

    await assuranceComponentsPage.clickOnCreateButton();

    await promise.all([
      assuranceUpdatePage.setDateDebutInput('dateDebut'),
      assuranceUpdatePage.setDateFinInput('dateFin'),
      assuranceUpdatePage.setPrixInput('5'),
      assuranceUpdatePage.vehiculeSelectLastOption()
    ]);

    expect(await assuranceUpdatePage.getDateDebutInput()).to.eq('dateDebut', 'Expected DateDebut value to be equals to dateDebut');
    expect(await assuranceUpdatePage.getDateFinInput()).to.eq('dateFin', 'Expected DateFin value to be equals to dateFin');
    expect(await assuranceUpdatePage.getPrixInput()).to.eq('5', 'Expected prix value to be equals to 5');

    await assuranceUpdatePage.save();
    expect(await assuranceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await assuranceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Assurance', async () => {
    const nbButtonsBeforeDelete = await assuranceComponentsPage.countDeleteButtons();
    await assuranceComponentsPage.clickOnLastDeleteButton();

    assuranceDeleteDialog = new AssuranceDeleteDialog();
    expect(await assuranceDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.assurance.delete.question');
    await assuranceDeleteDialog.clickOnConfirmButton();

    expect(await assuranceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
