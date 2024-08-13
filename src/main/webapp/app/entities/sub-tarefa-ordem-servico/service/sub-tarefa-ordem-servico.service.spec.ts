import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../sub-tarefa-ordem-servico.test-samples';

import { SubTarefaOrdemServicoService } from './sub-tarefa-ordem-servico.service';

const requireRestSample: ISubTarefaOrdemServico = {
  ...sampleWithRequiredData,
};

describe('SubTarefaOrdemServico Service', () => {
  let service: SubTarefaOrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubTarefaOrdemServico | ISubTarefaOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SubTarefaOrdemServicoService);
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

    it('should create a SubTarefaOrdemServico', () => {
      const subTarefaOrdemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subTarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubTarefaOrdemServico', () => {
      const subTarefaOrdemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subTarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubTarefaOrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubTarefaOrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SubTarefaOrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubTarefaOrdemServicoToCollectionIfMissing', () => {
      it('should add a SubTarefaOrdemServico to an empty array', () => {
        const subTarefaOrdemServico: ISubTarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing([], subTarefaOrdemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subTarefaOrdemServico);
      });

      it('should not add a SubTarefaOrdemServico to an array that contains it', () => {
        const subTarefaOrdemServico: ISubTarefaOrdemServico = sampleWithRequiredData;
        const subTarefaOrdemServicoCollection: ISubTarefaOrdemServico[] = [
          {
            ...subTarefaOrdemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing(subTarefaOrdemServicoCollection, subTarefaOrdemServico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubTarefaOrdemServico to an array that doesn't contain it", () => {
        const subTarefaOrdemServico: ISubTarefaOrdemServico = sampleWithRequiredData;
        const subTarefaOrdemServicoCollection: ISubTarefaOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing(subTarefaOrdemServicoCollection, subTarefaOrdemServico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subTarefaOrdemServico);
      });

      it('should add only unique SubTarefaOrdemServico to an array', () => {
        const subTarefaOrdemServicoArray: ISubTarefaOrdemServico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subTarefaOrdemServicoCollection: ISubTarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing(
          subTarefaOrdemServicoCollection,
          ...subTarefaOrdemServicoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subTarefaOrdemServico: ISubTarefaOrdemServico = sampleWithRequiredData;
        const subTarefaOrdemServico2: ISubTarefaOrdemServico = sampleWithPartialData;
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing([], subTarefaOrdemServico, subTarefaOrdemServico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subTarefaOrdemServico);
        expect(expectedResult).toContain(subTarefaOrdemServico2);
      });

      it('should accept null and undefined values', () => {
        const subTarefaOrdemServico: ISubTarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing([], null, subTarefaOrdemServico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subTarefaOrdemServico);
      });

      it('should return initial array if no SubTarefaOrdemServico is added', () => {
        const subTarefaOrdemServicoCollection: ISubTarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addSubTarefaOrdemServicoToCollectionIfMissing(subTarefaOrdemServicoCollection, undefined, null);
        expect(expectedResult).toEqual(subTarefaOrdemServicoCollection);
      });
    });

    describe('compareSubTarefaOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubTarefaOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareSubTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareSubTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareSubTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
