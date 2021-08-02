import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PayementComponentsPage, PayementDeleteDialog, PayementUpdatePage } from './payement.page-object';

const expect = chai.expect;

describe('Payement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payementComponentsPage: PayementComponentsPage;
  let payementUpdatePage: PayementUpdatePage;
  let payementDeleteDialog: PayementDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Payements', async () => {
    await navBarPage.goToEntity('payement');
    payementComponentsPage = new PayementComponentsPage();
    await browser.wait(ec.visibilityOf(payementComponentsPage.title), 5000);
    expect(await payementComponentsPage.getTitle()).to.eq('autoEcoleApp.payement.home.title');
    await browser.wait(ec.or(ec.visibilityOf(payementComponentsPage.entities), ec.visibilityOf(payementComponentsPage.noResult)), 1000);
  });

  it('should load create Payement page', async () => {
    await payementComponentsPage.clickOnCreateButton();
    payementUpdatePage = new PayementUpdatePage();
    expect(await payementUpdatePage.getPageTitle()).to.eq('autoEcoleApp.payement.home.createOrEditLabel');
    await payementUpdatePage.cancel();
  });

  it('should create and save Payements', async () => {
    const nbButtonsBeforeCreate = await payementComponentsPage.countDeleteButtons();

    await payementComponentsPage.clickOnCreateButton();

    await promise.all([
      payementUpdatePage.setTotalInput('5'),
      payementUpdatePage.setResteInput('5'),
      payementUpdatePage.setVenduInput('5'),
      payementUpdatePage.setRemarqueInput('remarque')
    ]);

    expect(await payementUpdatePage.getTotalInput()).to.eq('5', 'Expected total value to be equals to 5');
    expect(await payementUpdatePage.getResteInput()).to.eq('5', 'Expected reste value to be equals to 5');
    expect(await payementUpdatePage.getVenduInput()).to.eq('5', 'Expected vendu value to be equals to 5');
    expect(await payementUpdatePage.getRemarqueInput()).to.eq('remarque', 'Expected Remarque value to be equals to remarque');

    await payementUpdatePage.save();
    expect(await payementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await payementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Payement', async () => {
    const nbButtonsBeforeDelete = await payementComponentsPage.countDeleteButtons();
    await payementComponentsPage.clickOnLastDeleteButton();

    payementDeleteDialog = new PayementDeleteDialog();
    expect(await payementDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.payement.delete.question');
    await payementDeleteDialog.clickOnConfirmButton();

    expect(await payementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
