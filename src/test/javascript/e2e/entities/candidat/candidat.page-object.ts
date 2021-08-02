import { element, by, ElementFinder } from 'protractor';

export class CandidatComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-candidat div table .btn-danger'));
  title = element.all(by.css('jhi-candidat div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CandidatUpdatePage {
  pageTitle = element(by.id('jhi-candidat-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  cinInput = element(by.id('field_cin'));
  nomInput = element(by.id('field_nom'));
  prenomInput = element(by.id('field_prenom'));
  telephoneInput = element(by.id('field_telephone'));
  adresseInput = element(by.id('field_adresse'));
  situationInput = element(by.id('field_situation'));
  ageInput = element(by.id('field_age'));

  ecoleSelect = element(by.id('field_ecole'));
  payementSelect = element(by.id('field_payement'));
  formationSelect = element(by.id('field_formation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCinInput(cin: string): Promise<void> {
    await this.cinInput.sendKeys(cin);
  }

  async getCinInput(): Promise<string> {
    return await this.cinInput.getAttribute('value');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setPrenomInput(prenom: string): Promise<void> {
    await this.prenomInput.sendKeys(prenom);
  }

  async getPrenomInput(): Promise<string> {
    return await this.prenomInput.getAttribute('value');
  }

  async setTelephoneInput(telephone: string): Promise<void> {
    await this.telephoneInput.sendKeys(telephone);
  }

  async getTelephoneInput(): Promise<string> {
    return await this.telephoneInput.getAttribute('value');
  }

  async setAdresseInput(adresse: string): Promise<void> {
    await this.adresseInput.sendKeys(adresse);
  }

  async getAdresseInput(): Promise<string> {
    return await this.adresseInput.getAttribute('value');
  }

  async setSituationInput(situation: string): Promise<void> {
    await this.situationInput.sendKeys(situation);
  }

  async getSituationInput(): Promise<string> {
    return await this.situationInput.getAttribute('value');
  }

  async setAgeInput(age: string): Promise<void> {
    await this.ageInput.sendKeys(age);
  }

  async getAgeInput(): Promise<string> {
    return await this.ageInput.getAttribute('value');
  }

  async ecoleSelectLastOption(): Promise<void> {
    await this.ecoleSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async ecoleSelectOption(option: string): Promise<void> {
    await this.ecoleSelect.sendKeys(option);
  }

  getEcoleSelect(): ElementFinder {
    return this.ecoleSelect;
  }

  async getEcoleSelectedOption(): Promise<string> {
    return await this.ecoleSelect.element(by.css('option:checked')).getText();
  }

  async payementSelectLastOption(): Promise<void> {
    await this.payementSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async payementSelectOption(option: string): Promise<void> {
    await this.payementSelect.sendKeys(option);
  }

  getPayementSelect(): ElementFinder {
    return this.payementSelect;
  }

  async getPayementSelectedOption(): Promise<string> {
    return await this.payementSelect.element(by.css('option:checked')).getText();
  }

  async formationSelectLastOption(): Promise<void> {
    await this.formationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formationSelectOption(option: string): Promise<void> {
    await this.formationSelect.sendKeys(option);
  }

  getFormationSelect(): ElementFinder {
    return this.formationSelect;
  }

  async getFormationSelectedOption(): Promise<string> {
    return await this.formationSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CandidatDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-candidat-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-candidat'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
