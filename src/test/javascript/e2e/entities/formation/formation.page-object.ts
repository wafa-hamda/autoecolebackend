import { element, by, ElementFinder } from 'protractor';

export class FormationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-formation div table .btn-danger'));
  title = element.all(by.css('jhi-formation div h2#page-heading span')).first();
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

export class FormationUpdatePage {
  pageTitle = element(by.id('jhi-formation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dateDebutInput = element(by.id('field_dateDebut'));
  dateFinInput = element(by.id('field_dateFin'));
  nbrHeureCodeInput = element(by.id('field_nbrHeureCode'));
  prixheureCodeInput = element(by.id('field_prixheureCode'));
  nbrHeureConduitInput = element(by.id('field_nbrHeureConduit'));
  prixHeureConduitInput = element(by.id('field_prixHeureConduit'));
  disponobilteInput = element(by.id('field_disponobilte'));

  candidatSelect = element(by.id('field_candidat'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateDebutInput(dateDebut: string): Promise<void> {
    await this.dateDebutInput.sendKeys(dateDebut);
  }

  async getDateDebutInput(): Promise<string> {
    return await this.dateDebutInput.getAttribute('value');
  }

  async setDateFinInput(dateFin: string): Promise<void> {
    await this.dateFinInput.sendKeys(dateFin);
  }

  async getDateFinInput(): Promise<string> {
    return await this.dateFinInput.getAttribute('value');
  }

  async setNbrHeureCodeInput(nbrHeureCode: string): Promise<void> {
    await this.nbrHeureCodeInput.sendKeys(nbrHeureCode);
  }

  async getNbrHeureCodeInput(): Promise<string> {
    return await this.nbrHeureCodeInput.getAttribute('value');
  }

  async setPrixheureCodeInput(prixheureCode: string): Promise<void> {
    await this.prixheureCodeInput.sendKeys(prixheureCode);
  }

  async getPrixheureCodeInput(): Promise<string> {
    return await this.prixheureCodeInput.getAttribute('value');
  }

  async setNbrHeureConduitInput(nbrHeureConduit: string): Promise<void> {
    await this.nbrHeureConduitInput.sendKeys(nbrHeureConduit);
  }

  async getNbrHeureConduitInput(): Promise<string> {
    return await this.nbrHeureConduitInput.getAttribute('value');
  }

  async setPrixHeureConduitInput(prixHeureConduit: string): Promise<void> {
    await this.prixHeureConduitInput.sendKeys(prixHeureConduit);
  }

  async getPrixHeureConduitInput(): Promise<string> {
    return await this.prixHeureConduitInput.getAttribute('value');
  }

  async setDisponobilteInput(disponobilte: string): Promise<void> {
    await this.disponobilteInput.sendKeys(disponobilte);
  }

  async getDisponobilteInput(): Promise<string> {
    return await this.disponobilteInput.getAttribute('value');
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

export class FormationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-formation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-formation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
