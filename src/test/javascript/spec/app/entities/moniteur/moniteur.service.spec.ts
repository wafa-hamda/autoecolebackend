import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MoniteurService } from 'app/entities/moniteur/moniteur.service';
import { IMoniteur, Moniteur } from 'app/shared/model/moniteur.model';

describe('Service Tests', () => {
  describe('Moniteur Service', () => {
    let injector: TestBed;
    let service: MoniteurService;
    let httpMock: HttpTestingController;
    let elemDefault: IMoniteur;
    let expectedResult: IMoniteur | IMoniteur[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MoniteurService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Moniteur(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Moniteur', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Moniteur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Moniteur', () => {
        const returnedFromService = Object.assign(
          {
            cin: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            telephone: 'BBBBBB',
            type: 'BBBBBB',
            adresse: 'BBBBBB',
            datenais: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Moniteur', () => {
        const returnedFromService = Object.assign(
          {
            cin: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            telephone: 'BBBBBB',
            type: 'BBBBBB',
            adresse: 'BBBBBB',
            datenais: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Moniteur', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
