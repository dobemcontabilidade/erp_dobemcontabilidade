import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../contador-responsavel-ordem-servico.test-samples';

import { ContadorResponsavelOrdemServicoService, RestContadorResponsavelOrdemServico } from './contador-responsavel-ordem-servico.service';

const requireRestSample: RestContadorResponsavelOrdemServico = {
  ...sampleWithRequiredData,
  dataAtribuicao: sampleWithRequiredData.dataAtribuicao?.toJSON(),
  dataRevogacao: sampleWithRequiredData.dataRevogacao?.toJSON(),
};

describe('ContadorResponsavelOrdemServico Service', () => {
  let service: ContadorResponsavelOrdemServicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IContadorResponsavelOrdemServico | IContadorResponsavelOrdemServico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContadorResponsavelOrdemServicoService);
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

    it('should create a ContadorResponsavelOrdemServico', () => {
      const contadorResponsavelOrdemServico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contadorResponsavelOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContadorResponsavelOrdemServico', () => {
      const contadorResponsavelOrdemServico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contadorResponsavelOrdemServico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContadorResponsavelOrdemServico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContadorResponsavelOrdemServico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContadorResponsavelOrdemServico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContadorResponsavelOrdemServicoToCollectionIfMissing', () => {
      it('should add a ContadorResponsavelOrdemServico to an empty array', () => {
        const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = sampleWithRequiredData;
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing([], contadorResponsavelOrdemServico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contadorResponsavelOrdemServico);
      });

      it('should not add a ContadorResponsavelOrdemServico to an array that contains it', () => {
        const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = sampleWithRequiredData;
        const contadorResponsavelOrdemServicoCollection: IContadorResponsavelOrdemServico[] = [
          {
            ...contadorResponsavelOrdemServico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          contadorResponsavelOrdemServicoCollection,
          contadorResponsavelOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContadorResponsavelOrdemServico to an array that doesn't contain it", () => {
        const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = sampleWithRequiredData;
        const contadorResponsavelOrdemServicoCollection: IContadorResponsavelOrdemServico[] = [sampleWithPartialData];
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          contadorResponsavelOrdemServicoCollection,
          contadorResponsavelOrdemServico,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contadorResponsavelOrdemServico);
      });

      it('should add only unique ContadorResponsavelOrdemServico to an array', () => {
        const contadorResponsavelOrdemServicoArray: IContadorResponsavelOrdemServico[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const contadorResponsavelOrdemServicoCollection: IContadorResponsavelOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          contadorResponsavelOrdemServicoCollection,
          ...contadorResponsavelOrdemServicoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = sampleWithRequiredData;
        const contadorResponsavelOrdemServico2: IContadorResponsavelOrdemServico = sampleWithPartialData;
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          [],
          contadorResponsavelOrdemServico,
          contadorResponsavelOrdemServico2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contadorResponsavelOrdemServico);
        expect(expectedResult).toContain(contadorResponsavelOrdemServico2);
      });

      it('should accept null and undefined values', () => {
        const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = sampleWithRequiredData;
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          [],
          null,
          contadorResponsavelOrdemServico,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contadorResponsavelOrdemServico);
      });

      it('should return initial array if no ContadorResponsavelOrdemServico is added', () => {
        const contadorResponsavelOrdemServicoCollection: IContadorResponsavelOrdemServico[] = [sampleWithRequiredData];
        expectedResult = service.addContadorResponsavelOrdemServicoToCollectionIfMissing(
          contadorResponsavelOrdemServicoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(contadorResponsavelOrdemServicoCollection);
      });
    });

    describe('compareContadorResponsavelOrdemServico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContadorResponsavelOrdemServico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContadorResponsavelOrdemServico(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContadorResponsavelOrdemServico(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContadorResponsavelOrdemServico(entity1, entity2);
        const compareResult2 = service.compareContadorResponsavelOrdemServico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
