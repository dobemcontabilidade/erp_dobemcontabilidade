import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFormaDePagamento } from '../forma-de-pagamento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../forma-de-pagamento.test-samples';

import { FormaDePagamentoService } from './forma-de-pagamento.service';

const requireRestSample: IFormaDePagamento = {
  ...sampleWithRequiredData,
};

describe('FormaDePagamento Service', () => {
  let service: FormaDePagamentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFormaDePagamento | IFormaDePagamento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FormaDePagamentoService);
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

    it('should create a FormaDePagamento', () => {
      const formaDePagamento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(formaDePagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormaDePagamento', () => {
      const formaDePagamento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(formaDePagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FormaDePagamento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FormaDePagamento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FormaDePagamento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFormaDePagamentoToCollectionIfMissing', () => {
      it('should add a FormaDePagamento to an empty array', () => {
        const formaDePagamento: IFormaDePagamento = sampleWithRequiredData;
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing([], formaDePagamento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formaDePagamento);
      });

      it('should not add a FormaDePagamento to an array that contains it', () => {
        const formaDePagamento: IFormaDePagamento = sampleWithRequiredData;
        const formaDePagamentoCollection: IFormaDePagamento[] = [
          {
            ...formaDePagamento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing(formaDePagamentoCollection, formaDePagamento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormaDePagamento to an array that doesn't contain it", () => {
        const formaDePagamento: IFormaDePagamento = sampleWithRequiredData;
        const formaDePagamentoCollection: IFormaDePagamento[] = [sampleWithPartialData];
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing(formaDePagamentoCollection, formaDePagamento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formaDePagamento);
      });

      it('should add only unique FormaDePagamento to an array', () => {
        const formaDePagamentoArray: IFormaDePagamento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const formaDePagamentoCollection: IFormaDePagamento[] = [sampleWithRequiredData];
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing(formaDePagamentoCollection, ...formaDePagamentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formaDePagamento: IFormaDePagamento = sampleWithRequiredData;
        const formaDePagamento2: IFormaDePagamento = sampleWithPartialData;
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing([], formaDePagamento, formaDePagamento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formaDePagamento);
        expect(expectedResult).toContain(formaDePagamento2);
      });

      it('should accept null and undefined values', () => {
        const formaDePagamento: IFormaDePagamento = sampleWithRequiredData;
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing([], null, formaDePagamento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formaDePagamento);
      });

      it('should return initial array if no FormaDePagamento is added', () => {
        const formaDePagamentoCollection: IFormaDePagamento[] = [sampleWithRequiredData];
        expectedResult = service.addFormaDePagamentoToCollectionIfMissing(formaDePagamentoCollection, undefined, null);
        expect(expectedResult).toEqual(formaDePagamentoCollection);
      });
    });

    describe('compareFormaDePagamento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFormaDePagamento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFormaDePagamento(entity1, entity2);
        const compareResult2 = service.compareFormaDePagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFormaDePagamento(entity1, entity2);
        const compareResult2 = service.compareFormaDePagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFormaDePagamento(entity1, entity2);
        const compareResult2 = service.compareFormaDePagamento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
