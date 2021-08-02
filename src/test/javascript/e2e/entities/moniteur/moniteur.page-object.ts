import { element, by, ElementFinder } from 'protractor';

export class MoniteurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-moniteur div table .btn-danger'));
  title = element.all(by.css('jhi-moniteur div h2#page-heading span')).first();
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

export class MoniteurUpdatePage {
  pageTitle = element(by.id('jhi-moniteur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  cinInput = element(by.id('field_cin'));
  loginInput = element(by.id('field_login'));
  passwordInput = element(by.id('field_password'));
  nomInput = element(by.id('field_nom'));
  prenomInput = element(by.id('field_prenom'));
  telephoneInput = element(by.id('field_telephone'));
  typeInput = element(by.id('field_type'));
  adresseInput = element(by.id('field_adresse'));
  datenaisInput = element(by.id('field_datenais'));

  ecoleSelect = element(by.id('field_ecole'));
  vehiculeSelect = element(by.id('field_vehicule'));
  candidatSelect = element(by.id('field_candidat'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCinInput(cin: string): Promise<void> {
    await this.cinInput.sendKeys(cin);
  }

  async getCinInput(): Promise<string> {
    return await this.cinInput.getAttribute('value');
  }

  async setLoginInput(login: string): Promise<void> {
    await this.loginInput.sendKeys(login);
  }

  async getLoginInput(): Promise<string> {
    return await this.loginInput.getAttribute('value');
  }

  async setPasswordInput(password: string): Promise<void> {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput(): Promise<string> {
    return await this.passwordInput.getAttribute('value');
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

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async setAdresseInput(adresse: string): Promise<void> {
    await this.adresseInput.sendKeys(adresse);
  }

  async getAdresseInput(): Promise<string> {
    return await this.adresseInput.getAttribute('value');
  }

  async setDatenaisInput(datenais: string): Promise<void> {
    await this.datenaisInput.sendKeys(datenais);
  }

  async getDatenaisInput(): Promise<string> {
    return await this.datenaisInput.getAttribute('value');
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

  async vehiculeSelectLastOption(): Promise<void> {
    await this.vehiculeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vehiculeSelectOption(option: string): Promise<void> {
    await this.vehiculeSelect.sendKeys(option);
  }

  getVehiculeSelect(): ElementFinder {
    return this.vehiculeSelect;
  }

  async getVehiculeSelectedOption(): Promise<string> {
    return await this.vehiculeSelect.element(by.css('option:checked')).getText();
  }

  async candidatSelectLastOption(): Promise<void> {
    await this.candidatSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async candidatSelectOption(option: string): Promise<void> {
    await this.candidatSelect.sendKeys(option);
  }

  getCandidatSelect(): ElementFinder {
    return this.candidatSelect;
  }

  async getCandidatSelectedOption(): Promise<string> {
    return await this.candidatSelect.element(by.css('option:checked')).getText();
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

export class MoniteurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-moniteur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-moniteur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
