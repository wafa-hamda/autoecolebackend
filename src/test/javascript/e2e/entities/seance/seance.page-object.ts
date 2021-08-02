import { element, by, ElementFinder } from 'protractor';

export class SeanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-seance div table .btn-danger'));
  title = element.all(by.css('jhi-seance div h2#page-heading span')).first();
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

export class SeanceUpdatePage {
  pageTitle = element(by.id('jhi-seance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dateHeurePrevuInput = element(by.id('field_dateHeurePrevu'));
  dateHeureReelInput = element(by.id('field_dateHeureReel'));
  nbrHeureInput = element(by.id('field_nbrHeure'));
  typeInput = element(by.id('field_type'));

  moniteurSelect = element(by.id('field_moniteur'));
  candidatSelect = element(by.id('field_candidat'));
  vehiculeSelect = element(by.id('field_vehicule'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateHeurePrevuInput(dateHeurePrevu: string): Promise<void> {
    await this.dateHeurePrevuInput.sendKeys(dateHeurePrevu);
  }

  async getDateHeurePrevuInput(): Promise<string> {
    return await this.dateHeurePrevuInput.getAttribute('value');
  }

  async setDateHeureReelInput(dateHeureReel: string): Promise<void> {
    await this.dateHeureReelInput.sendKeys(dateHeureReel);
  }

  async getDateHeureReelInput(): Promise<string> {
    return await this.dateHeureReelInput.getAttribute('value');
  }

  async setNbrHeureInput(nbrHeure: string): Promise<void> {
    await this.nbrHeureInput.sendKeys(nbrHeure);
  }

  async getNbrHeureInput(): Promise<string> {
    return await this.nbrHeureInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
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

export class SeanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-seance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-seance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
