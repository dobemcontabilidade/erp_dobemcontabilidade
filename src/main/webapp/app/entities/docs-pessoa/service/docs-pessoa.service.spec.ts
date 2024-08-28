import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDocsPessoa } from '../docs-pessoa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../docs-pessoa.test-samples';

import { DocsPessoaService } from './docs-pessoa.service';

const requireRestSample: IDocsPessoa = {
  ...sampleWithRequiredData,
};

describe('DocsPessoa Service', () => {
  let service: DocsPessoaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocsPessoa | IDocsPessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DocsPessoaService);
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

    it('should create a DocsPessoa', () => {
      const docsPessoa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(docsPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocsPessoa', () => {
      const docsPessoa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(docsPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocsPessoa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocsPessoa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DocsPessoa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocsPessoaToCollectionIfMissing', () => {
      it('should add a DocsPessoa to an empty array', () => {
        const docsPessoa: IDocsPessoa = sampleWithRequiredData;
        expectedResult = service.addDocsPessoaToCollectionIfMissing([], docsPessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docsPessoa);
      });

      it('should not add a DocsPessoa to an array that contains it', () => {
        const docsPessoa: IDocsPessoa = sampleWithRequiredData;
        const docsPessoaCollection: IDocsPessoa[] = [
          {
            ...docsPessoa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocsPessoaToCollectionIfMissing(docsPessoaCollection, docsPessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocsPessoa to an array that doesn't contain it", () => {
        const docsPessoa: IDocsPessoa = sampleWithRequiredData;
        const docsPessoaCollection: IDocsPessoa[] = [sampleWithPartialData];
        expectedResult = service.addDocsPessoaToCollectionIfMissing(docsPessoaCollection, docsPessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docsPessoa);
      });

      it('should add only unique DocsPessoa to an array', () => {
        const docsPessoaArray: IDocsPessoa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const docsPessoaCollection: IDocsPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addDocsPessoaToCollectionIfMissing(docsPessoaCollection, ...docsPessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const docsPessoa: IDocsPessoa = sampleWithRequiredData;
        const docsPessoa2: IDocsPessoa = sampleWithPartialData;
        expectedResult = service.addDocsPessoaToCollectionIfMissing([], docsPessoa, docsPessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docsPessoa);
        expect(expectedResult).toContain(docsPessoa2);
      });

      it('should accept null and undefined values', () => {
        const docsPessoa: IDocsPessoa = sampleWithRequiredData;
        expectedResult = service.addDocsPessoaToCollectionIfMissing([], null, docsPessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docsPessoa);
      });

      it('should return initial array if no DocsPessoa is added', () => {
        const docsPessoaCollection: IDocsPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addDocsPessoaToCollectionIfMissing(docsPessoaCollection, undefined, null);
        expect(expectedResult).toEqual(docsPessoaCollection);
      });
    });

    describe('compareDocsPessoa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocsPessoa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocsPessoa(entity1, entity2);
        const compareResult2 = service.compareDocsPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocsPessoa(entity1, entity2);
        const compareResult2 = service.compareDocsPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocsPessoa(entity1, entity2);
        const compareResult2 = service.compareDocsPessoa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
