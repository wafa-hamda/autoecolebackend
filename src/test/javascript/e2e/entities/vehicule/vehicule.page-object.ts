import { element, by, ElementFinder } from 'protractor';

export class VehiculeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vehicule div table .btn-danger'));
  title = element.all(by.css('jhi-vehicule div h2#page-heading span')).first();
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

export class VehiculeUpdatePage {
  pageTitle = element(by.id('jhi-vehicule-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  matriculeInput = element(by.id('field_matricule'));
  marqueInput = element(by.id('field_marque'));
  typeInput = element(by.id('field_type'));

  ecoleSelect = element(by.id('field_ecole'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMatriculeInput(matricule: string): Promise<void> {
    await this.matriculeInput.sendKeys(matricule);
  }

  async getMatriculeInput(): Promise<string> {
    return await this.matriculeInput.getAttribute('value');
  }

  async setMarqueInput(marque: string): Promise<void> {
    await this.marqueInput.sendKeys(marque);
  }

  async getMarqueInput(): Promise<string> {
    return await this.marqueInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
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

export class VehiculeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vehicule-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vehicule'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
