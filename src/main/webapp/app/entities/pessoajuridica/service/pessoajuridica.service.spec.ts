import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPessoajuridica } from '../pessoajuridica.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pessoajuridica.test-samples';

import { PessoajuridicaService } from './pessoajuridica.service';

const requireRestSample: IPessoajuridica = {
  ...sampleWithRequiredData,
};

describe('Pessoajuridica Service', () => {
  let service: PessoajuridicaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPessoajuridica | IPessoajuridica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PessoajuridicaService);
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

    it('should create a Pessoajuridica', () => {
      const pessoajuridica = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pessoajuridica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pessoajuridica', () => {
      const pessoajuridica = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pessoajuridica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pessoajuridica', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pessoajuridica', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pessoajuridica', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPessoajuridicaToCollectionIfMissing', () => {
      it('should add a Pessoajuridica to an empty array', () => {
        const pessoajuridica: IPessoajuridica = sampleWithRequiredData;
        expectedResult = service.addPessoajuridicaToCollectionIfMissing([], pessoajuridica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoajuridica);
      });

      it('should not add a Pessoajuridica to an array that contains it', () => {
        const pessoajuridica: IPessoajuridica = sampleWithRequiredData;
        const pessoajuridicaCollection: IPessoajuridica[] = [
          {
            ...pessoajuridica,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPessoajuridicaToCollectionIfMissing(pessoajuridicaCollection, pessoajuridica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pessoajuridica to an array that doesn't contain it", () => {
        const pessoajuridica: IPessoajuridica = sampleWithRequiredData;
        const pessoajuridicaCollection: IPessoajuridica[] = [sampleWithPartialData];
        expectedResult = service.addPessoajuridicaToCollectionIfMissing(pessoajuridicaCollection, pessoajuridica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoajuridica);
      });

      it('should add only unique Pessoajuridica to an array', () => {
        const pessoajuridicaArray: IPessoajuridica[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pessoajuridicaCollection: IPessoajuridica[] = [sampleWithRequiredData];
        expectedResult = service.addPessoajuridicaToCollectionIfMissing(pessoajuridicaCollection, ...pessoajuridicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pessoajuridica: IPessoajuridica = sampleWithRequiredData;
        const pessoajuridica2: IPessoajuridica = sampleWithPartialData;
        expectedResult = service.addPessoajuridicaToCollectionIfMissing([], pessoajuridica, pessoajuridica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoajuridica);
        expect(expectedResult).toContain(pessoajuridica2);
      });

      it('should accept null and undefined values', () => {
        const pessoajuridica: IPessoajuridica = sampleWithRequiredData;
        expectedResult = service.addPessoajuridicaToCollectionIfMissing([], null, pessoajuridica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoajuridica);
      });

      it('should return initial array if no Pessoajuridica is added', () => {
        const pessoajuridicaCollection: IPessoajuridica[] = [sampleWithRequiredData];
        expectedResult = service.addPessoajuridicaToCollectionIfMissing(pessoajuridicaCollection, undefined, null);
        expect(expectedResult).toEqual(pessoajuridicaCollection);
      });
    });

    describe('comparePessoajuridica', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePessoajuridica(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePessoajuridica(entity1, entity2);
        const compareResult2 = service.comparePessoajuridica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePessoajuridica(entity1, entity2);
        const compareResult2 = service.comparePessoajuridica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePessoajuridica(entity1, entity2);
        const compareResult2 = service.comparePessoajuridica(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
