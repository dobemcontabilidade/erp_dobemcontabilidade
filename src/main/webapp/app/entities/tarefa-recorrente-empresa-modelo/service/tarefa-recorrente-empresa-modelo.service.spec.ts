import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../tarefa-recorrente-empresa-modelo.test-samples';

import { TarefaRecorrenteEmpresaModeloService } from './tarefa-recorrente-empresa-modelo.service';

const requireRestSample: ITarefaRecorrenteEmpresaModelo = {
  ...sampleWithRequiredData,
};

describe('TarefaRecorrenteEmpresaModelo Service', () => {
  let service: TarefaRecorrenteEmpresaModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefaRecorrenteEmpresaModelo | ITarefaRecorrenteEmpresaModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaRecorrenteEmpresaModeloService);
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

    it('should create a TarefaRecorrenteEmpresaModelo', () => {
      const tarefaRecorrenteEmpresaModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefaRecorrenteEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TarefaRecorrenteEmpresaModelo', () => {
      const tarefaRecorrenteEmpresaModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefaRecorrenteEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TarefaRecorrenteEmpresaModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TarefaRecorrenteEmpresaModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TarefaRecorrenteEmpresaModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaRecorrenteEmpresaModeloToCollectionIfMissing', () => {
      it('should add a TarefaRecorrenteEmpresaModelo to an empty array', () => {
        const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing([], tarefaRecorrenteEmpresaModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrenteEmpresaModelo);
      });

      it('should not add a TarefaRecorrenteEmpresaModelo to an array that contains it', () => {
        const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = sampleWithRequiredData;
        const tarefaRecorrenteEmpresaModeloCollection: ITarefaRecorrenteEmpresaModelo[] = [
          {
            ...tarefaRecorrenteEmpresaModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing(
          tarefaRecorrenteEmpresaModeloCollection,
          tarefaRecorrenteEmpresaModelo,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TarefaRecorrenteEmpresaModelo to an array that doesn't contain it", () => {
        const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = sampleWithRequiredData;
        const tarefaRecorrenteEmpresaModeloCollection: ITarefaRecorrenteEmpresaModelo[] = [sampleWithPartialData];
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing(
          tarefaRecorrenteEmpresaModeloCollection,
          tarefaRecorrenteEmpresaModelo,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrenteEmpresaModelo);
      });

      it('should add only unique TarefaRecorrenteEmpresaModelo to an array', () => {
        const tarefaRecorrenteEmpresaModeloArray: ITarefaRecorrenteEmpresaModelo[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const tarefaRecorrenteEmpresaModeloCollection: ITarefaRecorrenteEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing(
          tarefaRecorrenteEmpresaModeloCollection,
          ...tarefaRecorrenteEmpresaModeloArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = sampleWithRequiredData;
        const tarefaRecorrenteEmpresaModelo2: ITarefaRecorrenteEmpresaModelo = sampleWithPartialData;
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing(
          [],
          tarefaRecorrenteEmpresaModelo,
          tarefaRecorrenteEmpresaModelo2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrenteEmpresaModelo);
        expect(expectedResult).toContain(tarefaRecorrenteEmpresaModelo2);
      });

      it('should accept null and undefined values', () => {
        const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing([], null, tarefaRecorrenteEmpresaModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrenteEmpresaModelo);
      });

      it('should return initial array if no TarefaRecorrenteEmpresaModelo is added', () => {
        const tarefaRecorrenteEmpresaModeloCollection: ITarefaRecorrenteEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteEmpresaModeloToCollectionIfMissing(
          tarefaRecorrenteEmpresaModeloCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(tarefaRecorrenteEmpresaModeloCollection);
      });
    });

    describe('compareTarefaRecorrenteEmpresaModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefaRecorrenteEmpresaModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefaRecorrenteEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefaRecorrenteEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefaRecorrenteEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
