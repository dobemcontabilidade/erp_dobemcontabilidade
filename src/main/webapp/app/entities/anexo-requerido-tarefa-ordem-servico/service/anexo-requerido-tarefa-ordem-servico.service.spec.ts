import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-requerido-tarefa-ordem-servico.test-samples';

import { AnexoRequeridoTarefaOrdemServicoService } from './anexo-requerido-tarefa-ordem-servico.service';

const requireRestSample: IAnexoRequeridoTarefaOrdemServico = {
  ...sampleWithRequiredData,
};

describe('AnexoRequeridoTarefaOrdemServico Service', () => {
  let service: AnexoRequeridoTarefaOrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoRequeridoTarefaOrdemServico | IAnexoRequeridoTarefaOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoRequeridoTarefaOrdemServicoService);
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

    it('should create a AnexoRequeridoTarefaOrdemServico', () => {
      const anexoRequeridoTarefaOrdemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoRequeridoTarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoRequeridoTarefaOrdemServico', () => {
      const anexoRequeridoTarefaOrdemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoRequeridoTarefaOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoRequeridoTarefaOrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoRequeridoTarefaOrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoRequeridoTarefaOrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing', () => {
      it('should add a AnexoRequeridoTarefaOrdemServico to an empty array', () => {
        const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing([], anexoRequeridoTarefaOrdemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoTarefaOrdemServico);
      });

      it('should not add a AnexoRequeridoTarefaOrdemServico to an array that contains it', () => {
        const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = sampleWithRequiredData;
        const anexoRequeridoTarefaOrdemServicoCollection: IAnexoRequeridoTarefaOrdemServico[] = [
          {
            ...anexoRequeridoTarefaOrdemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          anexoRequeridoTarefaOrdemServicoCollection,
          anexoRequeridoTarefaOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoRequeridoTarefaOrdemServico to an array that doesn't contain it", () => {
        const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = sampleWithRequiredData;
        const anexoRequeridoTarefaOrdemServicoCollection: IAnexoRequeridoTarefaOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          anexoRequeridoTarefaOrdemServicoCollection,
          anexoRequeridoTarefaOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoTarefaOrdemServico);
      });

      it('should add only unique AnexoRequeridoTarefaOrdemServico to an array', () => {
        const anexoRequeridoTarefaOrdemServicoArray: IAnexoRequeridoTarefaOrdemServico[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoRequeridoTarefaOrdemServicoCollection: IAnexoRequeridoTarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          anexoRequeridoTarefaOrdemServicoCollection,
          ...anexoRequeridoTarefaOrdemServicoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = sampleWithRequiredData;
        const anexoRequeridoTarefaOrdemServico2: IAnexoRequeridoTarefaOrdemServico = sampleWithPartialData;
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          [],
          anexoRequeridoTarefaOrdemServico,
          anexoRequeridoTarefaOrdemServico2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoTarefaOrdemServico);
        expect(expectedResult).toContain(anexoRequeridoTarefaOrdemServico2);
      });

      it('should accept null and undefined values', () => {
        const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          [],
          null,
          anexoRequeridoTarefaOrdemServico,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoTarefaOrdemServico);
      });

      it('should return initial array if no AnexoRequeridoTarefaOrdemServico is added', () => {
        const anexoRequeridoTarefaOrdemServicoCollection: IAnexoRequeridoTarefaOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing(
          anexoRequeridoTarefaOrdemServicoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(anexoRequeridoTarefaOrdemServicoCollection);
      });
    });

    describe('compareAnexoRequeridoTarefaOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoRequeridoTarefaOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoRequeridoTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoRequeridoTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoRequeridoTarefaOrdemServico(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoTarefaOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
