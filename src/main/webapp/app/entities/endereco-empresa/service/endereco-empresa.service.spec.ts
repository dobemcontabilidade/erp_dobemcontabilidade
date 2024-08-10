import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../endereco-empresa.test-samples';

import { EnderecoEmpresaService } from './endereco-empresa.service';

const requireRestSample: IEnderecoEmpresa = {
  ...sampleWithRequiredData,
};

describe('EnderecoEmpresa Service', () => {
  let service: EnderecoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnderecoEmpresa | IEnderecoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EnderecoEmpresaService);
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

    it('should create a EnderecoEmpresa', () => {
      const enderecoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enderecoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnderecoEmpresa', () => {
      const enderecoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enderecoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnderecoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnderecoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EnderecoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnderecoEmpresaToCollectionIfMissing', () => {
      it('should add a EnderecoEmpresa to an empty array', () => {
        const enderecoEmpresa: IEnderecoEmpresa = sampleWithRequiredData;
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing([], enderecoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoEmpresa);
      });

      it('should not add a EnderecoEmpresa to an array that contains it', () => {
        const enderecoEmpresa: IEnderecoEmpresa = sampleWithRequiredData;
        const enderecoEmpresaCollection: IEnderecoEmpresa[] = [
          {
            ...enderecoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing(enderecoEmpresaCollection, enderecoEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnderecoEmpresa to an array that doesn't contain it", () => {
        const enderecoEmpresa: IEnderecoEmpresa = sampleWithRequiredData;
        const enderecoEmpresaCollection: IEnderecoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing(enderecoEmpresaCollection, enderecoEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoEmpresa);
      });

      it('should add only unique EnderecoEmpresa to an array', () => {
        const enderecoEmpresaArray: IEnderecoEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enderecoEmpresaCollection: IEnderecoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing(enderecoEmpresaCollection, ...enderecoEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enderecoEmpresa: IEnderecoEmpresa = sampleWithRequiredData;
        const enderecoEmpresa2: IEnderecoEmpresa = sampleWithPartialData;
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing([], enderecoEmpresa, enderecoEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoEmpresa);
        expect(expectedResult).toContain(enderecoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const enderecoEmpresa: IEnderecoEmpresa = sampleWithRequiredData;
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing([], null, enderecoEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoEmpresa);
      });

      it('should return initial array if no EnderecoEmpresa is added', () => {
        const enderecoEmpresaCollection: IEnderecoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoEmpresaToCollectionIfMissing(enderecoEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(enderecoEmpresaCollection);
      });
    });

    describe('compareEnderecoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnderecoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnderecoEmpresa(entity1, entity2);
        const compareResult2 = service.compareEnderecoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnderecoEmpresa(entity1, entity2);
        const compareResult2 = service.compareEnderecoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnderecoEmpresa(entity1, entity2);
        const compareResult2 = service.compareEnderecoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
