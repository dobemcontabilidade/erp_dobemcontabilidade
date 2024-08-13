import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../opcao-razao-social-empresa.test-samples';

import { OpcaoRazaoSocialEmpresaService } from './opcao-razao-social-empresa.service';

const requireRestSample: IOpcaoRazaoSocialEmpresa = {
  ...sampleWithRequiredData,
};

describe('OpcaoRazaoSocialEmpresa Service', () => {
  let service: OpcaoRazaoSocialEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IOpcaoRazaoSocialEmpresa | IOpcaoRazaoSocialEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OpcaoRazaoSocialEmpresaService);
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

    it('should create a OpcaoRazaoSocialEmpresa', () => {
      const opcaoRazaoSocialEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(opcaoRazaoSocialEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OpcaoRazaoSocialEmpresa', () => {
      const opcaoRazaoSocialEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(opcaoRazaoSocialEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OpcaoRazaoSocialEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OpcaoRazaoSocialEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OpcaoRazaoSocialEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOpcaoRazaoSocialEmpresaToCollectionIfMissing', () => {
      it('should add a OpcaoRazaoSocialEmpresa to an empty array', () => {
        const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = sampleWithRequiredData;
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing([], opcaoRazaoSocialEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opcaoRazaoSocialEmpresa);
      });

      it('should not add a OpcaoRazaoSocialEmpresa to an array that contains it', () => {
        const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = sampleWithRequiredData;
        const opcaoRazaoSocialEmpresaCollection: IOpcaoRazaoSocialEmpresa[] = [
          {
            ...opcaoRazaoSocialEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing(
          opcaoRazaoSocialEmpresaCollection,
          opcaoRazaoSocialEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OpcaoRazaoSocialEmpresa to an array that doesn't contain it", () => {
        const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = sampleWithRequiredData;
        const opcaoRazaoSocialEmpresaCollection: IOpcaoRazaoSocialEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing(
          opcaoRazaoSocialEmpresaCollection,
          opcaoRazaoSocialEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opcaoRazaoSocialEmpresa);
      });

      it('should add only unique OpcaoRazaoSocialEmpresa to an array', () => {
        const opcaoRazaoSocialEmpresaArray: IOpcaoRazaoSocialEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const opcaoRazaoSocialEmpresaCollection: IOpcaoRazaoSocialEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing(
          opcaoRazaoSocialEmpresaCollection,
          ...opcaoRazaoSocialEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = sampleWithRequiredData;
        const opcaoRazaoSocialEmpresa2: IOpcaoRazaoSocialEmpresa = sampleWithPartialData;
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing([], opcaoRazaoSocialEmpresa, opcaoRazaoSocialEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opcaoRazaoSocialEmpresa);
        expect(expectedResult).toContain(opcaoRazaoSocialEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa = sampleWithRequiredData;
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing([], null, opcaoRazaoSocialEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opcaoRazaoSocialEmpresa);
      });

      it('should return initial array if no OpcaoRazaoSocialEmpresa is added', () => {
        const opcaoRazaoSocialEmpresaCollection: IOpcaoRazaoSocialEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addOpcaoRazaoSocialEmpresaToCollectionIfMissing(opcaoRazaoSocialEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(opcaoRazaoSocialEmpresaCollection);
      });
    });

    describe('compareOpcaoRazaoSocialEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOpcaoRazaoSocialEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOpcaoRazaoSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoRazaoSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOpcaoRazaoSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoRazaoSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOpcaoRazaoSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoRazaoSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
