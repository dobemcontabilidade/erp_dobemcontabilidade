import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGatewayPagamento } from '../gateway-pagamento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../gateway-pagamento.test-samples';

import { GatewayPagamentoService } from './gateway-pagamento.service';

const requireRestSample: IGatewayPagamento = {
  ...sampleWithRequiredData,
};

describe('GatewayPagamento Service', () => {
  let service: GatewayPagamentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IGatewayPagamento | IGatewayPagamento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GatewayPagamentoService);
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

    it('should create a GatewayPagamento', () => {
      const gatewayPagamento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gatewayPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GatewayPagamento', () => {
      const gatewayPagamento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gatewayPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GatewayPagamento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GatewayPagamento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GatewayPagamento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGatewayPagamentoToCollectionIfMissing', () => {
      it('should add a GatewayPagamento to an empty array', () => {
        const gatewayPagamento: IGatewayPagamento = sampleWithRequiredData;
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing([], gatewayPagamento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gatewayPagamento);
      });

      it('should not add a GatewayPagamento to an array that contains it', () => {
        const gatewayPagamento: IGatewayPagamento = sampleWithRequiredData;
        const gatewayPagamentoCollection: IGatewayPagamento[] = [
          {
            ...gatewayPagamento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing(gatewayPagamentoCollection, gatewayPagamento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GatewayPagamento to an array that doesn't contain it", () => {
        const gatewayPagamento: IGatewayPagamento = sampleWithRequiredData;
        const gatewayPagamentoCollection: IGatewayPagamento[] = [sampleWithPartialData];
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing(gatewayPagamentoCollection, gatewayPagamento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gatewayPagamento);
      });

      it('should add only unique GatewayPagamento to an array', () => {
        const gatewayPagamentoArray: IGatewayPagamento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const gatewayPagamentoCollection: IGatewayPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing(gatewayPagamentoCollection, ...gatewayPagamentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gatewayPagamento: IGatewayPagamento = sampleWithRequiredData;
        const gatewayPagamento2: IGatewayPagamento = sampleWithPartialData;
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing([], gatewayPagamento, gatewayPagamento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gatewayPagamento);
        expect(expectedResult).toContain(gatewayPagamento2);
      });

      it('should accept null and undefined values', () => {
        const gatewayPagamento: IGatewayPagamento = sampleWithRequiredData;
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing([], null, gatewayPagamento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gatewayPagamento);
      });

      it('should return initial array if no GatewayPagamento is added', () => {
        const gatewayPagamentoCollection: IGatewayPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addGatewayPagamentoToCollectionIfMissing(gatewayPagamentoCollection, undefined, null);
        expect(expectedResult).toEqual(gatewayPagamentoCollection);
      });
    });

    describe('compareGatewayPagamento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGatewayPagamento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGatewayPagamento(entity1, entity2);
        const compareResult2 = service.compareGatewayPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGatewayPagamento(entity1, entity2);
        const compareResult2 = service.compareGatewayPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGatewayPagamento(entity1, entity2);
        const compareResult2 = service.compareGatewayPagamento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
