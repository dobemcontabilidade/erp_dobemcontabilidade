import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../gateway-assinatura-empresa.test-samples';

import { GatewayAssinaturaEmpresaService } from './gateway-assinatura-empresa.service';

const requireRestSample: IGatewayAssinaturaEmpresa = {
  ...sampleWithRequiredData,
};

describe('GatewayAssinaturaEmpresa Service', () => {
  let service: GatewayAssinaturaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IGatewayAssinaturaEmpresa | IGatewayAssinaturaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GatewayAssinaturaEmpresaService);
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

    it('should create a GatewayAssinaturaEmpresa', () => {
      const gatewayAssinaturaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gatewayAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GatewayAssinaturaEmpresa', () => {
      const gatewayAssinaturaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gatewayAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GatewayAssinaturaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GatewayAssinaturaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GatewayAssinaturaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGatewayAssinaturaEmpresaToCollectionIfMissing', () => {
      it('should add a GatewayAssinaturaEmpresa to an empty array', () => {
        const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing([], gatewayAssinaturaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gatewayAssinaturaEmpresa);
      });

      it('should not add a GatewayAssinaturaEmpresa to an array that contains it', () => {
        const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = sampleWithRequiredData;
        const gatewayAssinaturaEmpresaCollection: IGatewayAssinaturaEmpresa[] = [
          {
            ...gatewayAssinaturaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing(
          gatewayAssinaturaEmpresaCollection,
          gatewayAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GatewayAssinaturaEmpresa to an array that doesn't contain it", () => {
        const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = sampleWithRequiredData;
        const gatewayAssinaturaEmpresaCollection: IGatewayAssinaturaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing(
          gatewayAssinaturaEmpresaCollection,
          gatewayAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gatewayAssinaturaEmpresa);
      });

      it('should add only unique GatewayAssinaturaEmpresa to an array', () => {
        const gatewayAssinaturaEmpresaArray: IGatewayAssinaturaEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const gatewayAssinaturaEmpresaCollection: IGatewayAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing(
          gatewayAssinaturaEmpresaCollection,
          ...gatewayAssinaturaEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = sampleWithRequiredData;
        const gatewayAssinaturaEmpresa2: IGatewayAssinaturaEmpresa = sampleWithPartialData;
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing([], gatewayAssinaturaEmpresa, gatewayAssinaturaEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gatewayAssinaturaEmpresa);
        expect(expectedResult).toContain(gatewayAssinaturaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing([], null, gatewayAssinaturaEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gatewayAssinaturaEmpresa);
      });

      it('should return initial array if no GatewayAssinaturaEmpresa is added', () => {
        const gatewayAssinaturaEmpresaCollection: IGatewayAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGatewayAssinaturaEmpresaToCollectionIfMissing(gatewayAssinaturaEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(gatewayAssinaturaEmpresaCollection);
      });
    });

    describe('compareGatewayAssinaturaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGatewayAssinaturaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGatewayAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareGatewayAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGatewayAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareGatewayAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGatewayAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareGatewayAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
