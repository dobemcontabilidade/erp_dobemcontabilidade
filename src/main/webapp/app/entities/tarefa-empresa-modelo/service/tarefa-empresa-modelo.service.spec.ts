import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../tarefa-empresa-modelo.test-samples';

import { TarefaEmpresaModeloService, RestTarefaEmpresaModelo } from './tarefa-empresa-modelo.service';

const requireRestSample: RestTarefaEmpresaModelo = {
  ...sampleWithRequiredData,
  dataAdmin: sampleWithRequiredData.dataAdmin?.toJSON(),
  dataLegal: sampleWithRequiredData.dataLegal?.toJSON(),
};

describe('TarefaEmpresaModelo Service', () => {
  let service: TarefaEmpresaModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefaEmpresaModelo | ITarefaEmpresaModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaEmpresaModeloService);
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

    it('should create a TarefaEmpresaModelo', () => {
      const tarefaEmpresaModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefaEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TarefaEmpresaModelo', () => {
      const tarefaEmpresaModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefaEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TarefaEmpresaModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TarefaEmpresaModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TarefaEmpresaModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaEmpresaModeloToCollectionIfMissing', () => {
      it('should add a TarefaEmpresaModelo to an empty array', () => {
        const tarefaEmpresaModelo: ITarefaEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing([], tarefaEmpresaModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaEmpresaModelo);
      });

      it('should not add a TarefaEmpresaModelo to an array that contains it', () => {
        const tarefaEmpresaModelo: ITarefaEmpresaModelo = sampleWithRequiredData;
        const tarefaEmpresaModeloCollection: ITarefaEmpresaModelo[] = [
          {
            ...tarefaEmpresaModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing(tarefaEmpresaModeloCollection, tarefaEmpresaModelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TarefaEmpresaModelo to an array that doesn't contain it", () => {
        const tarefaEmpresaModelo: ITarefaEmpresaModelo = sampleWithRequiredData;
        const tarefaEmpresaModeloCollection: ITarefaEmpresaModelo[] = [sampleWithPartialData];
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing(tarefaEmpresaModeloCollection, tarefaEmpresaModelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaEmpresaModelo);
      });

      it('should add only unique TarefaEmpresaModelo to an array', () => {
        const tarefaEmpresaModeloArray: ITarefaEmpresaModelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tarefaEmpresaModeloCollection: ITarefaEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing(tarefaEmpresaModeloCollection, ...tarefaEmpresaModeloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefaEmpresaModelo: ITarefaEmpresaModelo = sampleWithRequiredData;
        const tarefaEmpresaModelo2: ITarefaEmpresaModelo = sampleWithPartialData;
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing([], tarefaEmpresaModelo, tarefaEmpresaModelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaEmpresaModelo);
        expect(expectedResult).toContain(tarefaEmpresaModelo2);
      });

      it('should accept null and undefined values', () => {
        const tarefaEmpresaModelo: ITarefaEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing([], null, tarefaEmpresaModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaEmpresaModelo);
      });

      it('should return initial array if no TarefaEmpresaModelo is added', () => {
        const tarefaEmpresaModeloCollection: ITarefaEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaEmpresaModeloToCollectionIfMissing(tarefaEmpresaModeloCollection, undefined, null);
        expect(expectedResult).toEqual(tarefaEmpresaModeloCollection);
      });
    });

    describe('compareTarefaEmpresaModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefaEmpresaModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefaEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefaEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefaEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareTarefaEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
