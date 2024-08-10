import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAssinaturaEmpresa } from '../assinatura-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../assinatura-empresa.test-samples';

import { AssinaturaEmpresaService, RestAssinaturaEmpresa } from './assinatura-empresa.service';

const requireRestSample: RestAssinaturaEmpresa = {
  ...sampleWithRequiredData,
  dataContratacao: sampleWithRequiredData.dataContratacao?.toJSON(),
  dataEncerramento: sampleWithRequiredData.dataEncerramento?.toJSON(),
};

describe('AssinaturaEmpresa Service', () => {
  let service: AssinaturaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssinaturaEmpresa | IAssinaturaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AssinaturaEmpresaService);
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

    it('should create a AssinaturaEmpresa', () => {
      const assinaturaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssinaturaEmpresa', () => {
      const assinaturaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssinaturaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssinaturaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssinaturaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssinaturaEmpresaToCollectionIfMissing', () => {
      it('should add a AssinaturaEmpresa to an empty array', () => {
        const assinaturaEmpresa: IAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing([], assinaturaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assinaturaEmpresa);
      });

      it('should not add a AssinaturaEmpresa to an array that contains it', () => {
        const assinaturaEmpresa: IAssinaturaEmpresa = sampleWithRequiredData;
        const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [
          {
            ...assinaturaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing(assinaturaEmpresaCollection, assinaturaEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssinaturaEmpresa to an array that doesn't contain it", () => {
        const assinaturaEmpresa: IAssinaturaEmpresa = sampleWithRequiredData;
        const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing(assinaturaEmpresaCollection, assinaturaEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assinaturaEmpresa);
      });

      it('should add only unique AssinaturaEmpresa to an array', () => {
        const assinaturaEmpresaArray: IAssinaturaEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing(assinaturaEmpresaCollection, ...assinaturaEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assinaturaEmpresa: IAssinaturaEmpresa = sampleWithRequiredData;
        const assinaturaEmpresa2: IAssinaturaEmpresa = sampleWithPartialData;
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing([], assinaturaEmpresa, assinaturaEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assinaturaEmpresa);
        expect(expectedResult).toContain(assinaturaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const assinaturaEmpresa: IAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing([], null, assinaturaEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assinaturaEmpresa);
      });

      it('should return initial array if no AssinaturaEmpresa is added', () => {
        const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAssinaturaEmpresaToCollectionIfMissing(assinaturaEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(assinaturaEmpresaCollection);
      });
    });

    describe('compareAssinaturaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssinaturaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
