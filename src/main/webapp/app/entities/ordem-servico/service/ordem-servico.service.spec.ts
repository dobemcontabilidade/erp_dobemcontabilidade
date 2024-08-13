import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrdemServico } from '../ordem-servico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ordem-servico.test-samples';

import { OrdemServicoService, RestOrdemServico } from './ordem-servico.service';

const requireRestSample: RestOrdemServico = {
  ...sampleWithRequiredData,
  prazo: sampleWithRequiredData.prazo?.toJSON(),
  dataCriacao: sampleWithRequiredData.dataCriacao?.toJSON(),
  dataHoraCancelamento: sampleWithRequiredData.dataHoraCancelamento?.toJSON(),
};

describe('OrdemServico Service', () => {
  let service: OrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrdemServico | IOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrdemServicoService);
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

    it('should create a OrdemServico', () => {
      const ordemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ordemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrdemServico', () => {
      const ordemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ordemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrdemServicoToCollectionIfMissing', () => {
      it('should add a OrdemServico to an empty array', () => {
        const ordemServico: IOrdemServico = sampleWithRequiredData;
        expectedResult = service.addOrdemServicoToCollectionIfMissing([], ordemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordemServico);
      });

      it('should not add a OrdemServico to an array that contains it', () => {
        const ordemServico: IOrdemServico = sampleWithRequiredData;
        const ordemServicoCollection: IOrdemServico[] = [
          {
            ...ordemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrdemServicoToCollectionIfMissing(ordemServicoCollection, ordemServico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrdemServico to an array that doesn't contain it", () => {
        const ordemServico: IOrdemServico = sampleWithRequiredData;
        const ordemServicoCollection: IOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addOrdemServicoToCollectionIfMissing(ordemServicoCollection, ordemServico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordemServico);
      });

      it('should add only unique OrdemServico to an array', () => {
        const ordemServicoArray: IOrdemServico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ordemServicoCollection: IOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addOrdemServicoToCollectionIfMissing(ordemServicoCollection, ...ordemServicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ordemServico: IOrdemServico = sampleWithRequiredData;
        const ordemServico2: IOrdemServico = sampleWithPartialData;
        expectedResult = service.addOrdemServicoToCollectionIfMissing([], ordemServico, ordemServico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordemServico);
        expect(expectedResult).toContain(ordemServico2);
      });

      it('should accept null and undefined values', () => {
        const ordemServico: IOrdemServico = sampleWithRequiredData;
        expectedResult = service.addOrdemServicoToCollectionIfMissing([], null, ordemServico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordemServico);
      });

      it('should return initial array if no OrdemServico is added', () => {
        const ordemServicoCollection: IOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addOrdemServicoToCollectionIfMissing(ordemServicoCollection, undefined, null);
        expect(expectedResult).toEqual(ordemServicoCollection);
      });
    });

    describe('compareOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrdemServico(entity1, entity2);
        const compareResult2 = service.compareOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrdemServico(entity1, entity2);
        const compareResult2 = service.compareOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrdemServico(entity1, entity2);
        const compareResult2 = service.compareOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
