import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../contador-responsavel-tarefa-recorrente.test-samples';

import {
  ContadorResponsavelTarefaRecorrenteService,
  RestContadorResponsavelTarefaRecorrente,
} from './contador-responsavel-tarefa-recorrente.service';

const requireRestSample: RestContadorResponsavelTarefaRecorrente = {
  ...sampleWithRequiredData,
  dataAtribuicao: sampleWithRequiredData.dataAtribuicao?.toJSON(),
  dataRevogacao: sampleWithRequiredData.dataRevogacao?.toJSON(),
};

describe('ContadorResponsavelTarefaRecorrente Service', () => {
  let service: ContadorResponsavelTarefaRecorrenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IContadorResponsavelTarefaRecorrente | IContadorResponsavelTarefaRecorrente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContadorResponsavelTarefaRecorrenteService);
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

    it('should create a ContadorResponsavelTarefaRecorrente', () => {
      const contadorResponsavelTarefaRecorrente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contadorResponsavelTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContadorResponsavelTarefaRecorrente', () => {
      const contadorResponsavelTarefaRecorrente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contadorResponsavelTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContadorResponsavelTarefaRecorrente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContadorResponsavelTarefaRecorrente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContadorResponsavelTarefaRecorrente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContadorResponsavelTarefaRecorrenteToCollectionIfMissing', () => {
      it('should add a ContadorResponsavelTarefaRecorrente to an empty array', () => {
        const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing([], contadorResponsavelTarefaRecorrente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contadorResponsavelTarefaRecorrente);
      });

      it('should not add a ContadorResponsavelTarefaRecorrente to an array that contains it', () => {
        const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = sampleWithRequiredData;
        const contadorResponsavelTarefaRecorrenteCollection: IContadorResponsavelTarefaRecorrente[] = [
          {
            ...contadorResponsavelTarefaRecorrente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          contadorResponsavelTarefaRecorrenteCollection,
          contadorResponsavelTarefaRecorrente,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContadorResponsavelTarefaRecorrente to an array that doesn't contain it", () => {
        const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = sampleWithRequiredData;
        const contadorResponsavelTarefaRecorrenteCollection: IContadorResponsavelTarefaRecorrente[] = [sampleWithPartialData];
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          contadorResponsavelTarefaRecorrenteCollection,
          contadorResponsavelTarefaRecorrente,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contadorResponsavelTarefaRecorrente);
      });

      it('should add only unique ContadorResponsavelTarefaRecorrente to an array', () => {
        const contadorResponsavelTarefaRecorrenteArray: IContadorResponsavelTarefaRecorrente[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const contadorResponsavelTarefaRecorrenteCollection: IContadorResponsavelTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          contadorResponsavelTarefaRecorrenteCollection,
          ...contadorResponsavelTarefaRecorrenteArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = sampleWithRequiredData;
        const contadorResponsavelTarefaRecorrente2: IContadorResponsavelTarefaRecorrente = sampleWithPartialData;
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          [],
          contadorResponsavelTarefaRecorrente,
          contadorResponsavelTarefaRecorrente2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contadorResponsavelTarefaRecorrente);
        expect(expectedResult).toContain(contadorResponsavelTarefaRecorrente2);
      });

      it('should accept null and undefined values', () => {
        const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          [],
          null,
          contadorResponsavelTarefaRecorrente,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contadorResponsavelTarefaRecorrente);
      });

      it('should return initial array if no ContadorResponsavelTarefaRecorrente is added', () => {
        const contadorResponsavelTarefaRecorrenteCollection: IContadorResponsavelTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addContadorResponsavelTarefaRecorrenteToCollectionIfMissing(
          contadorResponsavelTarefaRecorrenteCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(contadorResponsavelTarefaRecorrenteCollection);
      });
    });

    describe('compareContadorResponsavelTarefaRecorrente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContadorResponsavelTarefaRecorrente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContadorResponsavelTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContadorResponsavelTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContadorResponsavelTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
