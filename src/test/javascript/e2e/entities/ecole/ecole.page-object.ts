import { element, by, ElementFinder } from 'protractor';

export class EcoleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ecole div table .btn-danger'));
  title = element.all(by.css('jhi-ecole div h2#page-heading span')).first();
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

export class EcoleUpdatePage {
  pageTitle = element(by.id('jhi-ecole-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomInput = element(by.id('field_nom'));
  adresseInput = element(by.id('field_adresse'));
  dateCreationInput = element(by.id('field_dateCreation'));
  tarifsInput = element(by.id('field_tarifs'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setAdresseInput(adresse: string): Promise<void> {
    await this.adresseInput.sendKeys(adresse);
  }

  async getAdresseInput(): Promise<string> {
    return await this.adresseInput.getAttribute('value');
  }

  async setDateCreationInput(dateCreation: string): Promise<void> {
    await this.dateCreationInput.sendKeys(dateCreation);
  }

  async getDateCreationInput(): Promise<string> {
    return await this.dateCreationInput.getAttribute('value');
  }

  async setTarifsInput(tarifs: string): Promise<void> {
    await this.tarifsInput.sendKeys(tarifs);
  }

  async getTarifsInput(): Promise<string> {
    return await this.tarifsInput.getAttribute('value');
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

export class EcoleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ecole-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ecole'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
