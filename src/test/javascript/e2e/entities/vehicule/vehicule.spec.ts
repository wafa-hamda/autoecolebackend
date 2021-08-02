import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VehiculeComponentsPage, VehiculeDeleteDialog, VehiculeUpdatePage } from './vehicule.page-object';

const expect = chai.expect;

describe('Vehicule e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vehiculeComponentsPage: VehiculeComponentsPage;
  let vehiculeUpdatePage: VehiculeUpdatePage;
  let vehiculeDeleteDialog: VehiculeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Vehicules', async () => {
    await navBarPage.goToEntity('vehicule');
    vehiculeComponentsPage = new VehiculeComponentsPage();
    await browser.wait(ec.visibilityOf(vehiculeComponentsPage.title), 5000);
    expect(await vehiculeComponentsPage.getTitle()).to.eq('autoEcoleApp.vehicule.home.title');
    await browser.wait(ec.or(ec.visibilityOf(vehiculeComponentsPage.entities), ec.visibilityOf(vehiculeComponentsPage.noResult)), 1000);
  });

  it('should load create Vehicule page', async () => {
    await vehiculeComponentsPage.clickOnCreateButton();
    vehiculeUpdatePage = new VehiculeUpdatePage();
    expect(await vehiculeUpdatePage.getPageTitle()).to.eq('autoEcoleApp.vehicule.home.createOrEditLabel');
    await vehiculeUpdatePage.cancel();
  });

  it('should create and save Vehicules', async () => {
    const nbButtonsBeforeCreate = await vehiculeComponentsPage.countDeleteButtons();

    await vehiculeComponentsPage.clickOnCreateButton();

    await promise.all([
      vehiculeUpdatePage.setMatriculeInput('matricule'),
      vehiculeUpdatePage.setMarqueInput('marque'),
      vehiculeUpdatePage.setTypeInput('type'),
      vehiculeUpdatePage.ecoleSelectLastOption()
    ]);

    expect(await vehiculeUpdatePage.getMatriculeInput()).to.eq('matricule', 'Expected Matricule value to be equals to matricule');
    expect(await vehiculeUpdatePage.getMarqueInput()).to.eq('marque', 'Expected Marque value to be equals to marque');
    expect(await vehiculeUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await vehiculeUpdatePage.save();
    expect(await vehiculeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await vehiculeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Vehicule', async () => {
    const nbButtonsBeforeDelete = await vehiculeComponentsPage.countDeleteButtons();
    await vehiculeComponentsPage.clickOnLastDeleteButton();

    vehiculeDeleteDialog = new VehiculeDeleteDialog();
    expect(await vehiculeDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.vehicule.delete.question');
    await vehiculeDeleteDialog.clickOnConfirmButton();

    expect(await vehiculeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
