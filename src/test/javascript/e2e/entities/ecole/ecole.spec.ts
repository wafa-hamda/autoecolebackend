import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EcoleComponentsPage, EcoleDeleteDialog, EcoleUpdatePage } from './ecole.page-object';

const expect = chai.expect;

describe('Ecole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ecoleComponentsPage: EcoleComponentsPage;
  let ecoleUpdatePage: EcoleUpdatePage;
  let ecoleDeleteDialog: EcoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Ecoles', async () => {
    await navBarPage.goToEntity('ecole');
    ecoleComponentsPage = new EcoleComponentsPage();
    await browser.wait(ec.visibilityOf(ecoleComponentsPage.title), 5000);
    expect(await ecoleComponentsPage.getTitle()).to.eq('autoEcoleApp.ecole.home.title');
    await browser.wait(ec.or(ec.visibilityOf(ecoleComponentsPage.entities), ec.visibilityOf(ecoleComponentsPage.noResult)), 1000);
  });

  it('should load create Ecole page', async () => {
    await ecoleComponentsPage.clickOnCreateButton();
    ecoleUpdatePage = new EcoleUpdatePage();
    expect(await ecoleUpdatePage.getPageTitle()).to.eq('autoEcoleApp.ecole.home.createOrEditLabel');
    await ecoleUpdatePage.cancel();
  });

  it('should create and save Ecoles', async () => {
    const nbButtonsBeforeCreate = await ecoleComponentsPage.countDeleteButtons();

    await ecoleComponentsPage.clickOnCreateButton();

    await promise.all([
      ecoleUpdatePage.setNomInput('nom'),
      ecoleUpdatePage.setAdresseInput('adresse'),
      ecoleUpdatePage.setDateCreationInput('dateCreation'),
      ecoleUpdatePage.setTarifsInput('tarifs')
    ]);

    expect(await ecoleUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await ecoleUpdatePage.getAdresseInput()).to.eq('adresse', 'Expected Adresse value to be equals to adresse');
    expect(await ecoleUpdatePage.getDateCreationInput()).to.eq('dateCreation', 'Expected DateCreation value to be equals to dateCreation');
    expect(await ecoleUpdatePage.getTarifsInput()).to.eq('tarifs', 'Expected Tarifs value to be equals to tarifs');

    await ecoleUpdatePage.save();
    expect(await ecoleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ecoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Ecole', async () => {
    const nbButtonsBeforeDelete = await ecoleComponentsPage.countDeleteButtons();
    await ecoleComponentsPage.clickOnLastDeleteButton();

    ecoleDeleteDialog = new EcoleDeleteDialog();
    expect(await ecoleDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.ecole.delete.question');
    await ecoleDeleteDialog.clickOnConfirmButton();

    expect(await ecoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
