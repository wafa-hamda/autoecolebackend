import { element, by, ElementFinder } from 'protractor';

export class PayementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payement div table .btn-danger'));
  title = element.all(by.css('jhi-payement div h2#page-heading span')).first();
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

export class PayementUpdatePage {
  pageTitle = element(by.id('jhi-payement-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  totalInput = element(by.id('field_total'));
  resteInput = element(by.id('field_reste'));
  venduInput = element(by.id('field_vendu'));
  remarqueInput = element(by.id('field_remarque'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTotalInput(total: string): Promise<void> {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput(): Promise<string> {
    return await this.totalInput.getAttribute('value');
  }

  async setResteInput(reste: string): Promise<void> {
    await this.resteInput.sendKeys(reste);
  }

  async getResteInput(): Promise<string> {
    return await this.resteInput.getAttribute('value');
  }

  async setVenduInput(vendu: string): Promise<void> {
    await this.venduInput.sendKeys(vendu);
  }

  async getVenduInput(): Promise<string> {
    return await this.venduInput.getAttribute('value');
  }

  async setRemarqueInput(remarque: string): Promise<void> {
    await this.remarqueInput.sendKeys(remarque);
  }

  async getRemarqueInput(): Promise<string> {
    return await this.remarqueInput.getAttribute('value');
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

export class PayementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-payement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-payement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
