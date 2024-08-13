import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../escolaridade-pessoa.test-samples';

import { EscolaridadePessoaService } from './escolaridade-pessoa.service';

const requireRestSample: IEscolaridadePessoa = {
  ...sampleWithRequiredData,
};

describe('EscolaridadePessoa Service', () => {
  let service: EscolaridadePessoaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEscolaridadePessoa | IEscolaridadePessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EscolaridadePessoaService);
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

    it('should create a EscolaridadePessoa', () => {
      const escolaridadePessoa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(escolaridadePessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EscolaridadePessoa', () => {
      const escolaridadePessoa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(escolaridadePessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EscolaridadePessoa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EscolaridadePessoa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EscolaridadePessoa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEscolaridadePessoaToCollectionIfMissing', () => {
      it('should add a EscolaridadePessoa to an empty array', () => {
        const escolaridadePessoa: IEscolaridadePessoa = sampleWithRequiredData;
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing([], escolaridadePessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escolaridadePessoa);
      });

      it('should not add a EscolaridadePessoa to an array that contains it', () => {
        const escolaridadePessoa: IEscolaridadePessoa = sampleWithRequiredData;
        const escolaridadePessoaCollection: IEscolaridadePessoa[] = [
          {
            ...escolaridadePessoa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing(escolaridadePessoaCollection, escolaridadePessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EscolaridadePessoa to an array that doesn't contain it", () => {
        const escolaridadePessoa: IEscolaridadePessoa = sampleWithRequiredData;
        const escolaridadePessoaCollection: IEscolaridadePessoa[] = [sampleWithPartialData];
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing(escolaridadePessoaCollection, escolaridadePessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escolaridadePessoa);
      });

      it('should add only unique EscolaridadePessoa to an array', () => {
        const escolaridadePessoaArray: IEscolaridadePessoa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const escolaridadePessoaCollection: IEscolaridadePessoa[] = [sampleWithRequiredData];
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing(escolaridadePessoaCollection, ...escolaridadePessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const escolaridadePessoa: IEscolaridadePessoa = sampleWithRequiredData;
        const escolaridadePessoa2: IEscolaridadePessoa = sampleWithPartialData;
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing([], escolaridadePessoa, escolaridadePessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escolaridadePessoa);
        expect(expectedResult).toContain(escolaridadePessoa2);
      });

      it('should accept null and undefined values', () => {
        const escolaridadePessoa: IEscolaridadePessoa = sampleWithRequiredData;
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing([], null, escolaridadePessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escolaridadePessoa);
      });

      it('should return initial array if no EscolaridadePessoa is added', () => {
        const escolaridadePessoaCollection: IEscolaridadePessoa[] = [sampleWithRequiredData];
        expectedResult = service.addEscolaridadePessoaToCollectionIfMissing(escolaridadePessoaCollection, undefined, null);
        expect(expectedResult).toEqual(escolaridadePessoaCollection);
      });
    });

    describe('compareEscolaridadePessoa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEscolaridadePessoa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEscolaridadePessoa(entity1, entity2);
        const compareResult2 = service.compareEscolaridadePessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEscolaridadePessoa(entity1, entity2);
        const compareResult2 = service.compareEscolaridadePessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEscolaridadePessoa(entity1, entity2);
        const compareResult2 = service.compareEscolaridadePessoa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
