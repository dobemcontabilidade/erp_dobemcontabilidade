import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBancoContador } from '../banco-contador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../banco-contador.test-samples';

import { BancoContadorService } from './banco-contador.service';

const requireRestSample: IBancoContador = {
  ...sampleWithRequiredData,
};

describe('BancoContador Service', () => {
  let service: BancoContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IBancoContador | IBancoContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BancoContadorService);
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

    it('should create a BancoContador', () => {
      const bancoContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bancoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BancoContador', () => {
      const bancoContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bancoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BancoContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BancoContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BancoContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBancoContadorToCollectionIfMissing', () => {
      it('should add a BancoContador to an empty array', () => {
        const bancoContador: IBancoContador = sampleWithRequiredData;
        expectedResult = service.addBancoContadorToCollectionIfMissing([], bancoContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bancoContador);
      });

      it('should not add a BancoContador to an array that contains it', () => {
        const bancoContador: IBancoContador = sampleWithRequiredData;
        const bancoContadorCollection: IBancoContador[] = [
          {
            ...bancoContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBancoContadorToCollectionIfMissing(bancoContadorCollection, bancoContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BancoContador to an array that doesn't contain it", () => {
        const bancoContador: IBancoContador = sampleWithRequiredData;
        const bancoContadorCollection: IBancoContador[] = [sampleWithPartialData];
        expectedResult = service.addBancoContadorToCollectionIfMissing(bancoContadorCollection, bancoContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bancoContador);
      });

      it('should add only unique BancoContador to an array', () => {
        const bancoContadorArray: IBancoContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bancoContadorCollection: IBancoContador[] = [sampleWithRequiredData];
        expectedResult = service.addBancoContadorToCollectionIfMissing(bancoContadorCollection, ...bancoContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bancoContador: IBancoContador = sampleWithRequiredData;
        const bancoContador2: IBancoContador = sampleWithPartialData;
        expectedResult = service.addBancoContadorToCollectionIfMissing([], bancoContador, bancoContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bancoContador);
        expect(expectedResult).toContain(bancoContador2);
      });

      it('should accept null and undefined values', () => {
        const bancoContador: IBancoContador = sampleWithRequiredData;
        expectedResult = service.addBancoContadorToCollectionIfMissing([], null, bancoContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bancoContador);
      });

      it('should return initial array if no BancoContador is added', () => {
        const bancoContadorCollection: IBancoContador[] = [sampleWithRequiredData];
        expectedResult = service.addBancoContadorToCollectionIfMissing(bancoContadorCollection, undefined, null);
        expect(expectedResult).toEqual(bancoContadorCollection);
      });
    });

    describe('compareBancoContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBancoContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBancoContador(entity1, entity2);
        const compareResult2 = service.compareBancoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBancoContador(entity1, entity2);
        const compareResult2 = service.compareBancoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBancoContador(entity1, entity2);
        const compareResult2 = service.compareBancoContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
