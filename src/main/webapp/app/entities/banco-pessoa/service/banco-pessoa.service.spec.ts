import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBancoPessoa } from '../banco-pessoa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../banco-pessoa.test-samples';

import { BancoPessoaService } from './banco-pessoa.service';

const requireRestSample: IBancoPessoa = {
  ...sampleWithRequiredData,
};

describe('BancoPessoa Service', () => {
  let service: BancoPessoaService;
  let httpMock: HttpTestingController;
  let expectedResult: IBancoPessoa | IBancoPessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BancoPessoaService);
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

    it('should create a BancoPessoa', () => {
      const bancoPessoa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bancoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BancoPessoa', () => {
      const bancoPessoa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bancoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BancoPessoa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BancoPessoa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BancoPessoa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBancoPessoaToCollectionIfMissing', () => {
      it('should add a BancoPessoa to an empty array', () => {
        const bancoPessoa: IBancoPessoa = sampleWithRequiredData;
        expectedResult = service.addBancoPessoaToCollectionIfMissing([], bancoPessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bancoPessoa);
      });

      it('should not add a BancoPessoa to an array that contains it', () => {
        const bancoPessoa: IBancoPessoa = sampleWithRequiredData;
        const bancoPessoaCollection: IBancoPessoa[] = [
          {
            ...bancoPessoa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBancoPessoaToCollectionIfMissing(bancoPessoaCollection, bancoPessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BancoPessoa to an array that doesn't contain it", () => {
        const bancoPessoa: IBancoPessoa = sampleWithRequiredData;
        const bancoPessoaCollection: IBancoPessoa[] = [sampleWithPartialData];
        expectedResult = service.addBancoPessoaToCollectionIfMissing(bancoPessoaCollection, bancoPessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bancoPessoa);
      });

      it('should add only unique BancoPessoa to an array', () => {
        const bancoPessoaArray: IBancoPessoa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bancoPessoaCollection: IBancoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addBancoPessoaToCollectionIfMissing(bancoPessoaCollection, ...bancoPessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bancoPessoa: IBancoPessoa = sampleWithRequiredData;
        const bancoPessoa2: IBancoPessoa = sampleWithPartialData;
        expectedResult = service.addBancoPessoaToCollectionIfMissing([], bancoPessoa, bancoPessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bancoPessoa);
        expect(expectedResult).toContain(bancoPessoa2);
      });

      it('should accept null and undefined values', () => {
        const bancoPessoa: IBancoPessoa = sampleWithRequiredData;
        expectedResult = service.addBancoPessoaToCollectionIfMissing([], null, bancoPessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bancoPessoa);
      });

      it('should return initial array if no BancoPessoa is added', () => {
        const bancoPessoaCollection: IBancoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addBancoPessoaToCollectionIfMissing(bancoPessoaCollection, undefined, null);
        expect(expectedResult).toEqual(bancoPessoaCollection);
      });
    });

    describe('compareBancoPessoa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBancoPessoa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBancoPessoa(entity1, entity2);
        const compareResult2 = service.compareBancoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBancoPessoa(entity1, entity2);
        const compareResult2 = service.compareBancoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBancoPessoa(entity1, entity2);
        const compareResult2 = service.compareBancoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
