import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-requerido-pessoa.test-samples';

import { AnexoRequeridoPessoaService } from './anexo-requerido-pessoa.service';

const requireRestSample: IAnexoRequeridoPessoa = {
  ...sampleWithRequiredData,
};

describe('AnexoRequeridoPessoa Service', () => {
  let service: AnexoRequeridoPessoaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoRequeridoPessoa | IAnexoRequeridoPessoa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoRequeridoPessoaService);
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

    it('should create a AnexoRequeridoPessoa', () => {
      const anexoRequeridoPessoa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoRequeridoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoRequeridoPessoa', () => {
      const anexoRequeridoPessoa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoRequeridoPessoa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoRequeridoPessoa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoRequeridoPessoa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoRequeridoPessoa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoRequeridoPessoaToCollectionIfMissing', () => {
      it('should add a AnexoRequeridoPessoa to an empty array', () => {
        const anexoRequeridoPessoa: IAnexoRequeridoPessoa = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing([], anexoRequeridoPessoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoPessoa);
      });

      it('should not add a AnexoRequeridoPessoa to an array that contains it', () => {
        const anexoRequeridoPessoa: IAnexoRequeridoPessoa = sampleWithRequiredData;
        const anexoRequeridoPessoaCollection: IAnexoRequeridoPessoa[] = [
          {
            ...anexoRequeridoPessoa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing(anexoRequeridoPessoaCollection, anexoRequeridoPessoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoRequeridoPessoa to an array that doesn't contain it", () => {
        const anexoRequeridoPessoa: IAnexoRequeridoPessoa = sampleWithRequiredData;
        const anexoRequeridoPessoaCollection: IAnexoRequeridoPessoa[] = [sampleWithPartialData];
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing(anexoRequeridoPessoaCollection, anexoRequeridoPessoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoPessoa);
      });

      it('should add only unique AnexoRequeridoPessoa to an array', () => {
        const anexoRequeridoPessoaArray: IAnexoRequeridoPessoa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const anexoRequeridoPessoaCollection: IAnexoRequeridoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing(anexoRequeridoPessoaCollection, ...anexoRequeridoPessoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoRequeridoPessoa: IAnexoRequeridoPessoa = sampleWithRequiredData;
        const anexoRequeridoPessoa2: IAnexoRequeridoPessoa = sampleWithPartialData;
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing([], anexoRequeridoPessoa, anexoRequeridoPessoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoPessoa);
        expect(expectedResult).toContain(anexoRequeridoPessoa2);
      });

      it('should accept null and undefined values', () => {
        const anexoRequeridoPessoa: IAnexoRequeridoPessoa = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing([], null, anexoRequeridoPessoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoPessoa);
      });

      it('should return initial array if no AnexoRequeridoPessoa is added', () => {
        const anexoRequeridoPessoaCollection: IAnexoRequeridoPessoa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoPessoaToCollectionIfMissing(anexoRequeridoPessoaCollection, undefined, null);
        expect(expectedResult).toEqual(anexoRequeridoPessoaCollection);
      });
    });

    describe('compareAnexoRequeridoPessoa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoRequeridoPessoa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoRequeridoPessoa(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoRequeridoPessoa(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoRequeridoPessoa(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoPessoa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
