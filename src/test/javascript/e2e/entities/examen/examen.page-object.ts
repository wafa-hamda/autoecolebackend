import { element, by, ElementFinder } from 'protractor';

export class ExamenComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-examen div table .btn-danger'));
  title = element.all(by.css('jhi-examen div h2#page-heading span')).first();
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

export class ExamenUpdatePage {
  pageTitle = element(by.id('jhi-examen-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dateInput = element(by.id('field_date'));
  typeInput = element(by.id('field_type'));
  resultInput = element(by.id('field_result'));
  remarqueInput = element(by.id('field_remarque'));

  candidatSelect = element(by.id('field_candidat'));
  moniteurSelect = element(by.id('field_moniteur'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async setResultInput(result: string): Promise<void> {
    await this.resultInput.sendKeys(result);
  }

  async getResultInput(): Promise<string> {
    return await this.resultInput.getAttribute('value');
  }

  async setRemarqueInput(remarque: string): Promise<void> {
    await this.remarqueInput.sendKeys(remarque);
  }

  async getRemarqueInput(): Promise<string> {
    return await this.remarqueInput.getAttribute('value');
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

  async moniteurSelectLastOption(): Promise<void> {
    await this.moniteurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async moniteurSelectOption(option: string): Promise<void> {
    await this.moniteurSelect.sendKeys(option);
  }

  getMoniteurSelect(): ElementFinder {
    return this.moniteurSelect;
  }

  async getMoniteurSelectedOption(): Promise<string> {
    return await this.moniteurSelect.element(by.css('option:checked')).getText();
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

export class ExamenDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-examen-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-examen'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
