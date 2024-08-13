import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tarefa-ordem-servico.test-samples';

import { TarefaOrdemServicoService, RestTarefaOrdemServico } from './tarefa-ordem-servico.service';

const requireRestSample: RestTarefaOrdemServico = {
  ...sampleWithRequiredData,
  dataAdmin: sampleWithRequiredData.dataAdmin?.toJSON(),
};

describe('TarefaOrdemServico Service', () => {
  let service: TarefaOrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefaOrdemServico | ITarefaOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaOrdemServicoService);
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

    it('should create a TarefaOrdemServico', () => {
      const tarefaOrdemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TarefaOrdemServico', () => {
      const tarefaOrdemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TarefaOrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TarefaOrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TarefaOrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaOrdemServicoToCollectionIfMissing', () => {
      it('should add a TarefaOrdemServico to an empty array', () => {
        const tarefaOrdemServico: ITarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing([], tarefaOrdemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaOrdemServico);
      });

      it('should not add a TarefaOrdemServico to an array that contains it', () => {
        const tarefaOrdemServico: ITarefaOrdemServico = sampleWithRequiredData;
        const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [
          {
            ...tarefaOrdemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing(tarefaOrdemServicoCollection, tarefaOrdemServico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TarefaOrdemServico to an array that doesn't contain it", () => {
        const tarefaOrdemServico: ITarefaOrdemServico = sampleWithRequiredData;
        const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing(tarefaOrdemServicoCollection, tarefaOrdemServico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaOrdemServico);
      });

      it('should add only unique TarefaOrdemServico to an array', () => {
        const tarefaOrdemServicoArray: ITarefaOrdemServico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing(tarefaOrdemServicoCollection, ...tarefaOrdemServicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefaOrdemServico: ITarefaOrdemServico = sampleWithRequiredData;
        const tarefaOrdemServico2: ITarefaOrdemServico = sampleWithPartialData;
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing([], tarefaOrdemServico, tarefaOrdemServico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaOrdemServico);
        expect(expectedResult).toContain(tarefaOrdemServico2);
      });

      it('should accept null and undefined values', () => {
        const tarefaOrdemServico: ITarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing([], null, tarefaOrdemServico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaOrdemServico);
      });

      it('should return initial array if no TarefaOrdemServico is added', () => {
        const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaOrdemServicoToCollectionIfMissing(tarefaOrdemServicoCollection, undefined, null);
        expect(expectedResult).toEqual(tarefaOrdemServicoCollection);
      });
    });

    describe('compareTarefaOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefaOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
