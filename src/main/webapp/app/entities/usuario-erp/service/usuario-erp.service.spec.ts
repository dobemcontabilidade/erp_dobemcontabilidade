import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IUsuarioErp } from '../usuario-erp.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../usuario-erp.test-samples';

import { UsuarioErpService, RestUsuarioErp } from './usuario-erp.service';

const requireRestSample: RestUsuarioErp = {
  ...sampleWithRequiredData,
  dataHoraAtivacao: sampleWithRequiredData.dataHoraAtivacao?.toJSON(),
  dataLimiteAcesso: sampleWithRequiredData.dataLimiteAcesso?.toJSON(),
};

describe('UsuarioErp Service', () => {
  let service: UsuarioErpService;
  let httpMock: HttpTestingController;
  let expectedResult: IUsuarioErp | IUsuarioErp[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(UsuarioErpService);
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

    it('should create a UsuarioErp', () => {
      const usuarioErp = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(usuarioErp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UsuarioErp', () => {
      const usuarioErp = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(usuarioErp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UsuarioErp', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UsuarioErp', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UsuarioErp', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUsuarioErpToCollectionIfMissing', () => {
      it('should add a UsuarioErp to an empty array', () => {
        const usuarioErp: IUsuarioErp = sampleWithRequiredData;
        expectedResult = service.addUsuarioErpToCollectionIfMissing([], usuarioErp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioErp);
      });

      it('should not add a UsuarioErp to an array that contains it', () => {
        const usuarioErp: IUsuarioErp = sampleWithRequiredData;
        const usuarioErpCollection: IUsuarioErp[] = [
          {
            ...usuarioErp,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUsuarioErpToCollectionIfMissing(usuarioErpCollection, usuarioErp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UsuarioErp to an array that doesn't contain it", () => {
        const usuarioErp: IUsuarioErp = sampleWithRequiredData;
        const usuarioErpCollection: IUsuarioErp[] = [sampleWithPartialData];
        expectedResult = service.addUsuarioErpToCollectionIfMissing(usuarioErpCollection, usuarioErp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioErp);
      });

      it('should add only unique UsuarioErp to an array', () => {
        const usuarioErpArray: IUsuarioErp[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const usuarioErpCollection: IUsuarioErp[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioErpToCollectionIfMissing(usuarioErpCollection, ...usuarioErpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const usuarioErp: IUsuarioErp = sampleWithRequiredData;
        const usuarioErp2: IUsuarioErp = sampleWithPartialData;
        expectedResult = service.addUsuarioErpToCollectionIfMissing([], usuarioErp, usuarioErp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioErp);
        expect(expectedResult).toContain(usuarioErp2);
      });

      it('should accept null and undefined values', () => {
        const usuarioErp: IUsuarioErp = sampleWithRequiredData;
        expectedResult = service.addUsuarioErpToCollectionIfMissing([], null, usuarioErp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioErp);
      });

      it('should return initial array if no UsuarioErp is added', () => {
        const usuarioErpCollection: IUsuarioErp[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioErpToCollectionIfMissing(usuarioErpCollection, undefined, null);
        expect(expectedResult).toEqual(usuarioErpCollection);
      });
    });

    describe('compareUsuarioErp', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUsuarioErp(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUsuarioErp(entity1, entity2);
        const compareResult2 = service.compareUsuarioErp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUsuarioErp(entity1, entity2);
        const compareResult2 = service.compareUsuarioErp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUsuarioErp(entity1, entity2);
        const compareResult2 = service.compareUsuarioErp(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
