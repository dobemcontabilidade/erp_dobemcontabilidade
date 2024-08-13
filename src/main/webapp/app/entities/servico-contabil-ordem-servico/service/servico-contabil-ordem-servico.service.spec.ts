import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../servico-contabil-ordem-servico.test-samples';

import { ServicoContabilOrdemServicoService, RestServicoContabilOrdemServico } from './servico-contabil-ordem-servico.service';

const requireRestSample: RestServicoContabilOrdemServico = {
  ...sampleWithRequiredData,
  dataAdmin: sampleWithRequiredData.dataAdmin?.toJSON(),
  dataLegal: sampleWithRequiredData.dataLegal?.toJSON(),
};

describe('ServicoContabilOrdemServico Service', () => {
  let service: ServicoContabilOrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabilOrdemServico | IServicoContabilOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilOrdemServicoService);
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

    it('should create a ServicoContabilOrdemServico', () => {
      const servicoContabilOrdemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabilOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabilOrdemServico', () => {
      const servicoContabilOrdemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabilOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabilOrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabilOrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabilOrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilOrdemServicoToCollectionIfMissing', () => {
      it('should add a ServicoContabilOrdemServico to an empty array', () => {
        const servicoContabilOrdemServico: IServicoContabilOrdemServico = sampleWithRequiredData;
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing([], servicoContabilOrdemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilOrdemServico);
      });

      it('should not add a ServicoContabilOrdemServico to an array that contains it', () => {
        const servicoContabilOrdemServico: IServicoContabilOrdemServico = sampleWithRequiredData;
        const servicoContabilOrdemServicoCollection: IServicoContabilOrdemServico[] = [
          {
            ...servicoContabilOrdemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing(
          servicoContabilOrdemServicoCollection,
          servicoContabilOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabilOrdemServico to an array that doesn't contain it", () => {
        const servicoContabilOrdemServico: IServicoContabilOrdemServico = sampleWithRequiredData;
        const servicoContabilOrdemServicoCollection: IServicoContabilOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing(
          servicoContabilOrdemServicoCollection,
          servicoContabilOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilOrdemServico);
      });

      it('should add only unique ServicoContabilOrdemServico to an array', () => {
        const servicoContabilOrdemServicoArray: IServicoContabilOrdemServico[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const servicoContabilOrdemServicoCollection: IServicoContabilOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing(
          servicoContabilOrdemServicoCollection,
          ...servicoContabilOrdemServicoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabilOrdemServico: IServicoContabilOrdemServico = sampleWithRequiredData;
        const servicoContabilOrdemServico2: IServicoContabilOrdemServico = sampleWithPartialData;
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing(
          [],
          servicoContabilOrdemServico,
          servicoContabilOrdemServico2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilOrdemServico);
        expect(expectedResult).toContain(servicoContabilOrdemServico2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabilOrdemServico: IServicoContabilOrdemServico = sampleWithRequiredData;
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing([], null, servicoContabilOrdemServico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilOrdemServico);
      });

      it('should return initial array if no ServicoContabilOrdemServico is added', () => {
        const servicoContabilOrdemServicoCollection: IServicoContabilOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilOrdemServicoToCollectionIfMissing(
          servicoContabilOrdemServicoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(servicoContabilOrdemServicoCollection);
      });
    });

    describe('compareServicoContabilOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabilOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabilOrdemServico(entity1, entity2);
        const compareResult2 = service.compareServicoContabilOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabilOrdemServico(entity1, entity2);
        const compareResult2 = service.compareServicoContabilOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabilOrdemServico(entity1, entity2);
        const compareResult2 = service.compareServicoContabilOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
