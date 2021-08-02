import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ChargeComponentsPage, ChargeDeleteDialog, ChargeUpdatePage } from './charge.page-object';

const expect = chai.expect;

describe('Charge e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let chargeComponentsPage: ChargeComponentsPage;
  let chargeUpdatePage: ChargeUpdatePage;
  let chargeDeleteDialog: ChargeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Charges', async () => {
    await navBarPage.goToEntity('charge');
    chargeComponentsPage = new ChargeComponentsPage();
    await browser.wait(ec.visibilityOf(chargeComponentsPage.title), 5000);
    expect(await chargeComponentsPage.getTitle()).to.eq('autoEcoleApp.charge.home.title');
    await browser.wait(ec.or(ec.visibilityOf(chargeComponentsPage.entities), ec.visibilityOf(chargeComponentsPage.noResult)), 1000);
  });

  it('should load create Charge page', async () => {
    await chargeComponentsPage.clickOnCreateButton();
    chargeUpdatePage = new ChargeUpdatePage();
    expect(await chargeUpdatePage.getPageTitle()).to.eq('autoEcoleApp.charge.home.createOrEditLabel');
    await chargeUpdatePage.cancel();
  });

  it('should create and save Charges', async () => {
    const nbButtonsBeforeCreate = await chargeComponentsPage.countDeleteButtons();

    await chargeComponentsPage.clickOnCreateButton();

    await promise.all([
      chargeUpdatePage.setLibelleInput('libelle'),
      chargeUpdatePage.setPrixInput('5'),
      chargeUpdatePage.vehiculeSelectLastOption()
    ]);

    expect(await chargeUpdatePage.getLibelleInput()).to.eq('libelle', 'Expected Libelle value to be equals to libelle');
    expect(await chargeUpdatePage.getPrixInput()).to.eq('5', 'Expected prix value to be equals to 5');

    await chargeUpdatePage.save();
    expect(await chargeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await chargeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Charge', async () => {
    const nbButtonsBeforeDelete = await chargeComponentsPage.countDeleteButtons();
    await chargeComponentsPage.clickOnLastDeleteButton();

    chargeDeleteDialog = new ChargeDeleteDialog();
    expect(await chargeDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.charge.delete.question');
    await chargeDeleteDialog.clickOnConfirmButton();

    expect(await chargeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
