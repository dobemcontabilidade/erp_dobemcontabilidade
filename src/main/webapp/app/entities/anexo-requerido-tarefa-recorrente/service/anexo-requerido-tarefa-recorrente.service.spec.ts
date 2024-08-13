import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-requerido-tarefa-recorrente.test-samples';

import { AnexoRequeridoTarefaRecorrenteService } from './anexo-requerido-tarefa-recorrente.service';

const requireRestSample: IAnexoRequeridoTarefaRecorrente = {
  ...sampleWithRequiredData,
};

describe('AnexoRequeridoTarefaRecorrente Service', () => {
  let service: AnexoRequeridoTarefaRecorrenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoRequeridoTarefaRecorrente | IAnexoRequeridoTarefaRecorrente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoRequeridoTarefaRecorrenteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AnexoRequeridoTarefaRecorrente', () => {
      const anexoRequeridoTarefaRecorrente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoRequeridoTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoRequeridoTarefaRecorrente', () => {
      const anexoRequeridoTarefaRecorrente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoRequeridoTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoRequeridoTarefaRecorrente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoRequeridoTarefaRecorrente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoRequeridoTarefaRecorrente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing', () => {
      it('should add a AnexoRequeridoTarefaRecorrente to an empty array', () => {
        const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing([], anexoRequeridoTarefaRecorrente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoTarefaRecorrente);
      });

      it('should not add a AnexoRequeridoTarefaRecorrente to an array that contains it', () => {
        const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = sampleWithRequiredData;
        const anexoRequeridoTarefaRecorrenteCollection: IAnexoRequeridoTarefaRecorrente[] = [
          {
            ...anexoRequeridoTarefaRecorrente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          anexoRequeridoTarefaRecorrenteCollection,
          anexoRequeridoTarefaRecorrente,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoRequeridoTarefaRecorrente to an array that doesn't contain it", () => {
        const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = sampleWithRequiredData;
        const anexoRequeridoTarefaRecorrenteCollection: IAnexoRequeridoTarefaRecorrente[] = [sampleWithPartialData];
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          anexoRequeridoTarefaRecorrenteCollection,
          anexoRequeridoTarefaRecorrente,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoTarefaRecorrente);
      });

      it('should add only unique AnexoRequeridoTarefaRecorrente to an array', () => {
        const anexoRequeridoTarefaRecorrenteArray: IAnexoRequeridoTarefaRecorrente[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoRequeridoTarefaRecorrenteCollection: IAnexoRequeridoTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          anexoRequeridoTarefaRecorrenteCollection,
          ...anexoRequeridoTarefaRecorrenteArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = sampleWithRequiredData;
        const anexoRequeridoTarefaRecorrente2: IAnexoRequeridoTarefaRecorrente = sampleWithPartialData;
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          [],
          anexoRequeridoTarefaRecorrente,
          anexoRequeridoTarefaRecorrente2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoTarefaRecorrente);
        expect(expectedResult).toContain(anexoRequeridoTarefaRecorrente2);
      });

      it('should accept null and undefined values', () => {
        const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          [],
          null,
          anexoRequeridoTarefaRecorrente,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoTarefaRecorrente);
      });

      it('should return initial array if no AnexoRequeridoTarefaRecorrente is added', () => {
        const anexoRequeridoTarefaRecorrenteCollection: IAnexoRequeridoTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing(
          anexoRequeridoTarefaRecorrenteCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(anexoRequeridoTarefaRecorrenteCollection);
      });
    });

    describe('compareAnexoRequeridoTarefaRecorrente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoRequeridoTarefaRecorrente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoRequeridoTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoRequeridoTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoRequeridoTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
