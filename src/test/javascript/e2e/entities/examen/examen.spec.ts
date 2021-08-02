import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExamenComponentsPage, ExamenDeleteDialog, ExamenUpdatePage } from './examen.page-object';

const expect = chai.expect;

describe('Examen e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let examenComponentsPage: ExamenComponentsPage;
  let examenUpdatePage: ExamenUpdatePage;
  let examenDeleteDialog: ExamenDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Examen', async () => {
    await navBarPage.goToEntity('examen');
    examenComponentsPage = new ExamenComponentsPage();
    await browser.wait(ec.visibilityOf(examenComponentsPage.title), 5000);
    expect(await examenComponentsPage.getTitle()).to.eq('autoEcoleApp.examen.home.title');
    await browser.wait(ec.or(ec.visibilityOf(examenComponentsPage.entities), ec.visibilityOf(examenComponentsPage.noResult)), 1000);
  });

  it('should load create Examen page', async () => {
    await examenComponentsPage.clickOnCreateButton();
    examenUpdatePage = new ExamenUpdatePage();
    expect(await examenUpdatePage.getPageTitle()).to.eq('autoEcoleApp.examen.home.createOrEditLabel');
    await examenUpdatePage.cancel();
  });

  it('should create and save Examen', async () => {
    const nbButtonsBeforeCreate = await examenComponentsPage.countDeleteButtons();

    await examenComponentsPage.clickOnCreateButton();

    await promise.all([
      examenUpdatePage.setDateInput('date'),
      examenUpdatePage.setTypeInput('type'),
      examenUpdatePage.setResultInput('result'),
      examenUpdatePage.setRemarqueInput('remarque'),
      examenUpdatePage.candidatSelectLastOption(),
      examenUpdatePage.moniteurSelectLastOption()
    ]);

    expect(await examenUpdatePage.getDateInput()).to.eq('date', 'Expected Date value to be equals to date');
    expect(await examenUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');
    expect(await examenUpdatePage.getResultInput()).to.eq('result', 'Expected Result value to be equals to result');
    expect(await examenUpdatePage.getRemarqueInput()).to.eq('remarque', 'Expected Remarque value to be equals to remarque');

    await examenUpdatePage.save();
    expect(await examenUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await examenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Examen', async () => {
    const nbButtonsBeforeDelete = await examenComponentsPage.countDeleteButtons();
    await examenComponentsPage.clickOnLastDeleteButton();

    examenDeleteDialog = new ExamenDeleteDialog();
    expect(await examenDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.examen.delete.question');
    await examenDeleteDialog.clickOnConfirmButton();

    expect(await examenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
