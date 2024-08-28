import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../desconto-periodo-pagamento.test-samples';

import { DescontoPeriodoPagamentoService } from './desconto-periodo-pagamento.service';

const requireRestSample: IDescontoPeriodoPagamento = {
  ...sampleWithRequiredData,
};

describe('DescontoPeriodoPagamento Service', () => {
  let service: DescontoPeriodoPagamentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IDescontoPeriodoPagamento | IDescontoPeriodoPagamento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DescontoPeriodoPagamentoService);
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

    it('should create a DescontoPeriodoPagamento', () => {
      const descontoPeriodoPagamento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(descontoPeriodoPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DescontoPeriodoPagamento', () => {
      const descontoPeriodoPagamento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(descontoPeriodoPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DescontoPeriodoPagamento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DescontoPeriodoPagamento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DescontoPeriodoPagamento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDescontoPeriodoPagamentoToCollectionIfMissing', () => {
      it('should add a DescontoPeriodoPagamento to an empty array', () => {
        const descontoPeriodoPagamento: IDescontoPeriodoPagamento = sampleWithRequiredData;
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing([], descontoPeriodoPagamento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPeriodoPagamento);
      });

      it('should not add a DescontoPeriodoPagamento to an array that contains it', () => {
        const descontoPeriodoPagamento: IDescontoPeriodoPagamento = sampleWithRequiredData;
        const descontoPeriodoPagamentoCollection: IDescontoPeriodoPagamento[] = [
          {
            ...descontoPeriodoPagamento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing(
          descontoPeriodoPagamentoCollection,
          descontoPeriodoPagamento,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DescontoPeriodoPagamento to an array that doesn't contain it", () => {
        const descontoPeriodoPagamento: IDescontoPeriodoPagamento = sampleWithRequiredData;
        const descontoPeriodoPagamentoCollection: IDescontoPeriodoPagamento[] = [sampleWithPartialData];
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing(
          descontoPeriodoPagamentoCollection,
          descontoPeriodoPagamento,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPeriodoPagamento);
      });

      it('should add only unique DescontoPeriodoPagamento to an array', () => {
        const descontoPeriodoPagamentoArray: IDescontoPeriodoPagamento[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const descontoPeriodoPagamentoCollection: IDescontoPeriodoPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing(
          descontoPeriodoPagamentoCollection,
          ...descontoPeriodoPagamentoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const descontoPeriodoPagamento: IDescontoPeriodoPagamento = sampleWithRequiredData;
        const descontoPeriodoPagamento2: IDescontoPeriodoPagamento = sampleWithPartialData;
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing([], descontoPeriodoPagamento, descontoPeriodoPagamento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPeriodoPagamento);
        expect(expectedResult).toContain(descontoPeriodoPagamento2);
      });

      it('should accept null and undefined values', () => {
        const descontoPeriodoPagamento: IDescontoPeriodoPagamento = sampleWithRequiredData;
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing([], null, descontoPeriodoPagamento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPeriodoPagamento);
      });

      it('should return initial array if no DescontoPeriodoPagamento is added', () => {
        const descontoPeriodoPagamentoCollection: IDescontoPeriodoPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPeriodoPagamentoToCollectionIfMissing(descontoPeriodoPagamentoCollection, undefined, null);
        expect(expectedResult).toEqual(descontoPeriodoPagamentoCollection);
      });
    });

    describe('compareDescontoPeriodoPagamento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDescontoPeriodoPagamento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDescontoPeriodoPagamento(entity1, entity2);
        const compareResult2 = service.compareDescontoPeriodoPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDescontoPeriodoPagamento(entity1, entity2);
        const compareResult2 = service.compareDescontoPeriodoPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDescontoPeriodoPagamento(entity1, entity2);
        const compareResult2 = service.compareDescontoPeriodoPagamento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
