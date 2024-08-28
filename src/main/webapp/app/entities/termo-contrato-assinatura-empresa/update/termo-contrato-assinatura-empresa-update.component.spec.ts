import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITermoContratoContabil } from 'app/entities/termo-contrato-contabil/termo-contrato-contabil.model';
import { TermoContratoContabilService } from 'app/entities/termo-contrato-contabil/service/termo-contrato-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';
import { TermoContratoAssinaturaEmpresaService } from '../service/termo-contrato-assinatura-empresa.service';
import { TermoContratoAssinaturaEmpresaFormService } from './termo-contrato-assinatura-empresa-form.service';

import { TermoContratoAssinaturaEmpresaUpdateComponent } from './termo-contrato-assinatura-empresa-update.component';

describe('TermoContratoAssinaturaEmpresa Management Update Component', () => {
  let comp: TermoContratoAssinaturaEmpresaUpdateComponent;
  let fixture: ComponentFixture<TermoContratoAssinaturaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let termoContratoAssinaturaEmpresaFormService: TermoContratoAssinaturaEmpresaFormService;
  let termoContratoAssinaturaEmpresaService: TermoContratoAssinaturaEmpresaService;
  let termoContratoContabilService: TermoContratoContabilService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoContratoAssinaturaEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TermoContratoAssinaturaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TermoContratoAssinaturaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    termoContratoAssinaturaEmpresaFormService = TestBed.inject(TermoContratoAssinaturaEmpresaFormService);
    termoContratoAssinaturaEmpresaService = TestBed.inject(TermoContratoAssinaturaEmpresaService);
    termoContratoContabilService = TestBed.inject(TermoContratoContabilService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TermoContratoContabil query and add missing value', () => {
      const termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa = { id: 456 };
      const termoContratoContabil: ITermoContratoContabil = { id: 17439 };
      termoContratoAssinaturaEmpresa.termoContratoContabil = termoContratoContabil;

      const termoContratoContabilCollection: ITermoContratoContabil[] = [{ id: 13428 }];
      jest.spyOn(termoContratoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: termoContratoContabilCollection })));
      const additionalTermoContratoContabils = [termoContratoContabil];
      const expectedCollection: ITermoContratoContabil[] = [...additionalTermoContratoContabils, ...termoContratoContabilCollection];
      jest.spyOn(termoContratoContabilService, 'addTermoContratoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoContratoAssinaturaEmpresa });
      comp.ngOnInit();

      expect(termoContratoContabilService.query).toHaveBeenCalled();
      expect(termoContratoContabilService.addTermoContratoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        termoContratoContabilCollection,
        ...additionalTermoContratoContabils.map(expect.objectContaining),
      );
      expect(comp.termoContratoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa = { id: 456 };
      const empresa: IAssinaturaEmpresa = { id: 2487 };
      termoContratoAssinaturaEmpresa.empresa = empresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 4687 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [empresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoContratoAssinaturaEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa = { id: 456 };
      const termoContratoContabil: ITermoContratoContabil = { id: 20353 };
      termoContratoAssinaturaEmpresa.termoContratoContabil = termoContratoContabil;
      const empresa: IAssinaturaEmpresa = { id: 8176 };
      termoContratoAssinaturaEmpresa.empresa = empresa;

      activatedRoute.data = of({ termoContratoAssinaturaEmpresa });
      comp.ngOnInit();

      expect(comp.termoContratoContabilsSharedCollection).toContain(termoContratoContabil);
      expect(comp.assinaturaEmpresasSharedCollection).toContain(empresa);
      expect(comp.termoContratoAssinaturaEmpresa).toEqual(termoContratoAssinaturaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoAssinaturaEmpresa>>();
      const termoContratoAssinaturaEmpresa = { id: 123 };
      jest
        .spyOn(termoContratoAssinaturaEmpresaFormService, 'getTermoContratoAssinaturaEmpresa')
        .mockReturnValue(termoContratoAssinaturaEmpresa);
      jest.spyOn(termoContratoAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoContratoAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(termoContratoAssinaturaEmpresaFormService.getTermoContratoAssinaturaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(termoContratoAssinaturaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(termoContratoAssinaturaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoAssinaturaEmpresa>>();
      const termoContratoAssinaturaEmpresa = { id: 123 };
      jest.spyOn(termoContratoAssinaturaEmpresaFormService, 'getTermoContratoAssinaturaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(termoContratoAssinaturaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoAssinaturaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoContratoAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(termoContratoAssinaturaEmpresaFormService.getTermoContratoAssinaturaEmpresa).toHaveBeenCalled();
      expect(termoContratoAssinaturaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoAssinaturaEmpresa>>();
      const termoContratoAssinaturaEmpresa = { id: 123 };
      jest.spyOn(termoContratoAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(termoContratoAssinaturaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTermoContratoContabil', () => {
      it('Should forward to termoContratoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(termoContratoContabilService, 'compareTermoContratoContabil');
        comp.compareTermoContratoContabil(entity, entity2);
        expect(termoContratoContabilService.compareTermoContratoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
