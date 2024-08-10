import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoEmpresa } from '../anexo-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../anexo-empresa.test-samples';

import { AnexoEmpresaService } from './anexo-empresa.service';

const requireRestSample: IAnexoEmpresa = {
  ...sampleWithRequiredData,
};

describe('AnexoEmpresa Service', () => {
  let service: AnexoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoEmpresa | IAnexoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoEmpresaService);
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

    it('should create a AnexoEmpresa', () => {
      const anexoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoEmpresa', () => {
      const anexoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoEmpresaToCollectionIfMissing', () => {
      it('should add a AnexoEmpresa to an empty array', () => {
        const anexoEmpresa: IAnexoEmpresa = sampleWithRequiredData;
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing([], anexoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoEmpresa);
      });

      it('should not add a AnexoEmpresa to an array that contains it', () => {
        const anexoEmpresa: IAnexoEmpresa = sampleWithRequiredData;
        const anexoEmpresaCollection: IAnexoEmpresa[] = [
          {
            ...anexoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing(anexoEmpresaCollection, anexoEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoEmpresa to an array that doesn't contain it", () => {
        const anexoEmpresa: IAnexoEmpresa = sampleWithRequiredData;
        const anexoEmpresaCollection: IAnexoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing(anexoEmpresaCollection, anexoEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoEmpresa);
      });

      it('should add only unique AnexoEmpresa to an array', () => {
        const anexoEmpresaArray: IAnexoEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const anexoEmpresaCollection: IAnexoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing(anexoEmpresaCollection, ...anexoEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoEmpresa: IAnexoEmpresa = sampleWithRequiredData;
        const anexoEmpresa2: IAnexoEmpresa = sampleWithPartialData;
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing([], anexoEmpresa, anexoEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoEmpresa);
        expect(expectedResult).toContain(anexoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const anexoEmpresa: IAnexoEmpresa = sampleWithRequiredData;
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing([], null, anexoEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoEmpresa);
      });

      it('should return initial array if no AnexoEmpresa is added', () => {
        const anexoEmpresaCollection: IAnexoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoEmpresaToCollectionIfMissing(anexoEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(anexoEmpresaCollection);
      });
    });

    describe('compareAnexoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
