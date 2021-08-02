import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CandidatComponentsPage, CandidatDeleteDialog, CandidatUpdatePage } from './candidat.page-object';

const expect = chai.expect;

describe('Candidat e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let candidatComponentsPage: CandidatComponentsPage;
  let candidatUpdatePage: CandidatUpdatePage;
  let candidatDeleteDialog: CandidatDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Candidats', async () => {
    await navBarPage.goToEntity('candidat');
    candidatComponentsPage = new CandidatComponentsPage();
    await browser.wait(ec.visibilityOf(candidatComponentsPage.title), 5000);
    expect(await candidatComponentsPage.getTitle()).to.eq('autoEcoleApp.candidat.home.title');
    await browser.wait(ec.or(ec.visibilityOf(candidatComponentsPage.entities), ec.visibilityOf(candidatComponentsPage.noResult)), 1000);
  });

  it('should load create Candidat page', async () => {
    await candidatComponentsPage.clickOnCreateButton();
    candidatUpdatePage = new CandidatUpdatePage();
    expect(await candidatUpdatePage.getPageTitle()).to.eq('autoEcoleApp.candidat.home.createOrEditLabel');
    await candidatUpdatePage.cancel();
  });

  it('should create and save Candidats', async () => {
    const nbButtonsBeforeCreate = await candidatComponentsPage.countDeleteButtons();

    await candidatComponentsPage.clickOnCreateButton();

    await promise.all([
      candidatUpdatePage.setCinInput('cin'),
      candidatUpdatePage.setNomInput('nom'),
      candidatUpdatePage.setPrenomInput('prenom'),
      candidatUpdatePage.setTelephoneInput('telephone'),
      candidatUpdatePage.setAdresseInput('adresse'),
      candidatUpdatePage.setSituationInput('situation'),
      candidatUpdatePage.setAgeInput('5'),
      candidatUpdatePage.ecoleSelectLastOption(),
      candidatUpdatePage.payementSelectLastOption(),
      candidatUpdatePage.formationSelectLastOption()
    ]);

    expect(await candidatUpdatePage.getCinInput()).to.eq('cin', 'Expected Cin value to be equals to cin');
    expect(await candidatUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await candidatUpdatePage.getPrenomInput()).to.eq('prenom', 'Expected Prenom value to be equals to prenom');
    expect(await candidatUpdatePage.getTelephoneInput()).to.eq('telephone', 'Expected Telephone value to be equals to telephone');
    expect(await candidatUpdatePage.getAdresseInput()).to.eq('adresse', 'Expected Adresse value to be equals to adresse');
    expect(await candidatUpdatePage.getSituationInput()).to.eq('situation', 'Expected Situation value to be equals to situation');
    expect(await candidatUpdatePage.getAgeInput()).to.eq('5', 'Expected age value to be equals to 5');

    await candidatUpdatePage.save();
    expect(await candidatUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await candidatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Candidat', async () => {
    const nbButtonsBeforeDelete = await candidatComponentsPage.countDeleteButtons();
    await candidatComponentsPage.clickOnLastDeleteButton();

    candidatDeleteDialog = new CandidatDeleteDialog();
    expect(await candidatDeleteDialog.getDialogTitle()).to.eq('autoEcoleApp.candidat.delete.question');
    await candidatDeleteDialog.clickOnConfirmButton();

    expect(await candidatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
