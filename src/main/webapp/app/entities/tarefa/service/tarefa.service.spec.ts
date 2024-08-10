import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefa } from '../tarefa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tarefa.test-samples';

import { TarefaService, RestTarefa } from './tarefa.service';

const requireRestSample: RestTarefa = {
  ...sampleWithRequiredData,
  dataLegal: sampleWithRequiredData.dataLegal?.toJSON(),
};

describe('Tarefa Service', () => {
  let service: TarefaService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefa | ITarefa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaService);
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

    it('should create a Tarefa', () => {
      const tarefa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tarefa', () => {
      const tarefa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tarefa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tarefa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Tarefa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaToCollectionIfMissing', () => {
      it('should add a Tarefa to an empty array', () => {
        const tarefa: ITarefa = sampleWithRequiredData;
        expectedResult = service.addTarefaToCollectionIfMissing([], tarefa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefa);
      });

      it('should not add a Tarefa to an array that contains it', () => {
        const tarefa: ITarefa = sampleWithRequiredData;
        const tarefaCollection: ITarefa[] = [
          {
            ...tarefa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaToCollectionIfMissing(tarefaCollection, tarefa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tarefa to an array that doesn't contain it", () => {
        const tarefa: ITarefa = sampleWithRequiredData;
        const tarefaCollection: ITarefa[] = [sampleWithPartialData];
        expectedResult = service.addTarefaToCollectionIfMissing(tarefaCollection, tarefa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefa);
      });

      it('should add only unique Tarefa to an array', () => {
        const tarefaArray: ITarefa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tarefaCollection: ITarefa[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaToCollectionIfMissing(tarefaCollection, ...tarefaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefa: ITarefa = sampleWithRequiredData;
        const tarefa2: ITarefa = sampleWithPartialData;
        expectedResult = service.addTarefaToCollectionIfMissing([], tarefa, tarefa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefa);
        expect(expectedResult).toContain(tarefa2);
      });

      it('should accept null and undefined values', () => {
        const tarefa: ITarefa = sampleWithRequiredData;
        expectedResult = service.addTarefaToCollectionIfMissing([], null, tarefa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefa);
      });

      it('should return initial array if no Tarefa is added', () => {
        const tarefaCollection: ITarefa[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaToCollectionIfMissing(tarefaCollection, undefined, null);
        expect(expectedResult).toEqual(tarefaCollection);
      });
    });

    describe('compareTarefa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefa(entity1, entity2);
        const compareResult2 = service.compareTarefa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefa(entity1, entity2);
        const compareResult2 = service.compareTarefa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefa(entity1, entity2);
        const compareResult2 = service.compareTarefa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
