import { element, by, ElementFinder } from 'protractor';

export class ChargeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-charge div table .btn-danger'));
  title = element.all(by.css('jhi-charge div h2#page-heading span')).first();
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

export class ChargeUpdatePage {
  pageTitle = element(by.id('jhi-charge-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  libelleInput = element(by.id('field_libelle'));
  prixInput = element(by.id('field_prix'));

  vehiculeSelect = element(by.id('field_vehicule'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLibelleInput(libelle: string): Promise<void> {
    await this.libelleInput.sendKeys(libelle);
  }

  async getLibelleInput(): Promise<string> {
    return await this.libelleInput.getAttribute('value');
  }

  async setPrixInput(prix: string): Promise<void> {
    await this.prixInput.sendKeys(prix);
  }

  async getPrixInput(): Promise<string> {
    return await this.prixInput.getAttribute('value');
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

export class ChargeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-charge-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-charge'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
