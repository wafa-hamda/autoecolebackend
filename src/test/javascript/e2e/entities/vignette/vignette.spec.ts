import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VignetteComponentsPage, VignetteDeleteDialog, VignetteUpdatePage } from './vignette.page-object';

const expect = chai.expect;

describe('Vignette e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vignetteComponentsPage: VignetteComponentsPage;
  let vignetteUpdatePage: VignetteUpdatePage;
  let vignetteDeleteDialog: VignetteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Vignettes', async () => {
    await navBarPage.goToEntity('vignette');
    vignetteComponentsPage = new VignetteComponentsPage();
    await browser.wait(ec.visibilityOf(vignetteComponentsPage.title), 5000);
    expect(await vignetteComponentsPage.getTitle()).to.eq('autoEcoleApp.vignette.home.title');
    await browser.wait(ec.or(ec.visibilityOf(vignetteComponentsPage.entities), ec.visibilityOf(vignetteComponentsPage.noResult)), 1000);
  });

  it('should load create Vignette page', async () => {
    await vignetteComponentsPage.clickOnCreateButton();
    vignetteUpdatePage = new VignetteUpdatePage();
    expect(await vignetteUpdatePage.getPageTitle()).to.eq('autoEcoleApp.vignette.home.createOrEditLabel');
    await vignetteUpdatePage.cancel();
  });

  it('should create and save Vignettes', async () => {
    const nbButtonsBeforeCreate = await vignetteComponentsPage.countDeleteButtons();

    await vignetteComponentsPage.clickOnCreateButton();

    await promise.all([
      vignetteUpdatePage.setDateDebutInput('dateDebut'),
      vignetteUpdatePage.setDateFinInput('dateFin'),
      vignetteUpdatePage.setPrixInput('5'),
      vignetteUpdatePage.vehiculeSelectLastOption()
    ]);

    expect(await vignetteUpdatePage.getDateDebutInput()).to.eq('dateDebut', 'Expected DateDebut value to be equals to dateDebut');
    expect(await vignetteUpdatePage.getDateFinInput()).to.eq('dateFin', 'Expected DateFin value to be equals to dateFin');
    expect(await vignetteUpdatePage.getPrixInput()).to.eq('5', 'Expected prix value to be equals to 5');

    await vignetteUpdatePage.save();
    expect(await vignetteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await vignetteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Vignette', async () => {
    const nbButtonsBeforeDelete = await vignetteComponentsPage.countDeleteButtons();
    await vignetteComponentsPage.clickOnLastDeleteButton();

    vignetteDeleteDialog = new VignetteDeleteDialog();
    expect(await vignetteDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.vignette.delete.question');
    await vignetteDeleteDialog.clickOnConfirmButton();

    expect(await vignetteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
