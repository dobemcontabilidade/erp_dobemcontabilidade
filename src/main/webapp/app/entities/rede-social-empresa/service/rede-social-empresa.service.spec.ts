import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../rede-social-empresa.test-samples';

import { RedeSocialEmpresaService } from './rede-social-empresa.service';

const requireRestSample: IRedeSocialEmpresa = {
  ...sampleWithRequiredData,
};

describe('RedeSocialEmpresa Service', () => {
  let service: RedeSocialEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IRedeSocialEmpresa | IRedeSocialEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RedeSocialEmpresaService);
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

    it('should create a RedeSocialEmpresa', () => {
      const redeSocialEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(redeSocialEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RedeSocialEmpresa', () => {
      const redeSocialEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(redeSocialEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RedeSocialEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RedeSocialEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RedeSocialEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRedeSocialEmpresaToCollectionIfMissing', () => {
      it('should add a RedeSocialEmpresa to an empty array', () => {
        const redeSocialEmpresa: IRedeSocialEmpresa = sampleWithRequiredData;
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing([], redeSocialEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(redeSocialEmpresa);
      });

      it('should not add a RedeSocialEmpresa to an array that contains it', () => {
        const redeSocialEmpresa: IRedeSocialEmpresa = sampleWithRequiredData;
        const redeSocialEmpresaCollection: IRedeSocialEmpresa[] = [
          {
            ...redeSocialEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing(redeSocialEmpresaCollection, redeSocialEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RedeSocialEmpresa to an array that doesn't contain it", () => {
        const redeSocialEmpresa: IRedeSocialEmpresa = sampleWithRequiredData;
        const redeSocialEmpresaCollection: IRedeSocialEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing(redeSocialEmpresaCollection, redeSocialEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(redeSocialEmpresa);
      });

      it('should add only unique RedeSocialEmpresa to an array', () => {
        const redeSocialEmpresaArray: IRedeSocialEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const redeSocialEmpresaCollection: IRedeSocialEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing(redeSocialEmpresaCollection, ...redeSocialEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const redeSocialEmpresa: IRedeSocialEmpresa = sampleWithRequiredData;
        const redeSocialEmpresa2: IRedeSocialEmpresa = sampleWithPartialData;
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing([], redeSocialEmpresa, redeSocialEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(redeSocialEmpresa);
        expect(expectedResult).toContain(redeSocialEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const redeSocialEmpresa: IRedeSocialEmpresa = sampleWithRequiredData;
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing([], null, redeSocialEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(redeSocialEmpresa);
      });

      it('should return initial array if no RedeSocialEmpresa is added', () => {
        const redeSocialEmpresaCollection: IRedeSocialEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addRedeSocialEmpresaToCollectionIfMissing(redeSocialEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(redeSocialEmpresaCollection);
      });
    });

    describe('compareRedeSocialEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRedeSocialEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRedeSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareRedeSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRedeSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareRedeSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRedeSocialEmpresa(entity1, entity2);
        const compareResult2 = service.compareRedeSocialEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
