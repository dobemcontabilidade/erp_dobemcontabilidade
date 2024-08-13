import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../endereco-pessoa.test-samples';

import { EnderecoPessoaService } from './endereco-pessoa.service';

const requireRestSample: IEnderecoPessoa = {
  ...sampleWithRequiredData,
};

describe('EnderecoPessoa Service', () => {
  let service: EnderecoPessoaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnderecoPessoa | IEnderecoPessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EnderecoPessoaService);
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

    it('should create a EnderecoPessoa', () => {
      const enderecoPessoa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enderecoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnderecoPessoa', () => {
      const enderecoPessoa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enderecoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnderecoPessoa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnderecoPessoa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EnderecoPessoa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnderecoPessoaToCollectionIfMissing', () => {
      it('should add a EnderecoPessoa to an empty array', () => {
        const enderecoPessoa: IEnderecoPessoa = sampleWithRequiredData;
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing([], enderecoPessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoPessoa);
      });

      it('should not add a EnderecoPessoa to an array that contains it', () => {
        const enderecoPessoa: IEnderecoPessoa = sampleWithRequiredData;
        const enderecoPessoaCollection: IEnderecoPessoa[] = [
          {
            ...enderecoPessoa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing(enderecoPessoaCollection, enderecoPessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnderecoPessoa to an array that doesn't contain it", () => {
        const enderecoPessoa: IEnderecoPessoa = sampleWithRequiredData;
        const enderecoPessoaCollection: IEnderecoPessoa[] = [sampleWithPartialData];
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing(enderecoPessoaCollection, enderecoPessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoPessoa);
      });

      it('should add only unique EnderecoPessoa to an array', () => {
        const enderecoPessoaArray: IEnderecoPessoa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enderecoPessoaCollection: IEnderecoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing(enderecoPessoaCollection, ...enderecoPessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enderecoPessoa: IEnderecoPessoa = sampleWithRequiredData;
        const enderecoPessoa2: IEnderecoPessoa = sampleWithPartialData;
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing([], enderecoPessoa, enderecoPessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoPessoa);
        expect(expectedResult).toContain(enderecoPessoa2);
      });

      it('should accept null and undefined values', () => {
        const enderecoPessoa: IEnderecoPessoa = sampleWithRequiredData;
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing([], null, enderecoPessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoPessoa);
      });

      it('should return initial array if no EnderecoPessoa is added', () => {
        const enderecoPessoaCollection: IEnderecoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoPessoaToCollectionIfMissing(enderecoPessoaCollection, undefined, null);
        expect(expectedResult).toEqual(enderecoPessoaCollection);
      });
    });

    describe('compareEnderecoPessoa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnderecoPessoa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnderecoPessoa(entity1, entity2);
        const compareResult2 = service.compareEnderecoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnderecoPessoa(entity1, entity2);
        const compareResult2 = service.compareEnderecoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnderecoPessoa(entity1, entity2);
        const compareResult2 = service.compareEnderecoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
