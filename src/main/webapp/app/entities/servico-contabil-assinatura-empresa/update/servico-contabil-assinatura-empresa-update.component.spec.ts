import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from '../service/servico-contabil-assinatura-empresa.service';
import { ServicoContabilAssinaturaEmpresaFormService } from './servico-contabil-assinatura-empresa-form.service';

import { ServicoContabilAssinaturaEmpresaUpdateComponent } from './servico-contabil-assinatura-empresa-update.component';

describe('ServicoContabilAssinaturaEmpresa Management Update Component', () => {
  let comp: ServicoContabilAssinaturaEmpresaUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilAssinaturaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilAssinaturaEmpresaFormService: ServicoContabilAssinaturaEmpresaFormService;
  let servicoContabilAssinaturaEmpresaService: ServicoContabilAssinaturaEmpresaService;
  let servicoContabilService: ServicoContabilService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilAssinaturaEmpresaUpdateComponent],
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
      .overrideTemplate(ServicoContabilAssinaturaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilAssinaturaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilAssinaturaEmpresaFormService = TestBed.inject(ServicoContabilAssinaturaEmpresaFormService);
    servicoContabilAssinaturaEmpresaService = TestBed.inject(ServicoContabilAssinaturaEmpresaService);
    servicoContabilService = TestBed.inject(ServicoContabilService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabil query and add missing value', () => {
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 17811 };
      servicoContabilAssinaturaEmpresa.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 17360 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 8260 };
      servicoContabilAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 32362 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 26932 };
      servicoContabilAssinaturaEmpresa.servicoContabil = servicoContabil;
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 1949 };
      servicoContabilAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa });
      comp.ngOnInit();

      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.servicoContabilAssinaturaEmpresa).toEqual(servicoContabilAssinaturaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilAssinaturaEmpresa>>();
      const servicoContabilAssinaturaEmpresa = { id: 123 };
      jest
        .spyOn(servicoContabilAssinaturaEmpresaFormService, 'getServicoContabilAssinaturaEmpresa')
        .mockReturnValue(servicoContabilAssinaturaEmpresa);
      jest.spyOn(servicoContabilAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilAssinaturaEmpresaFormService.getServicoContabilAssinaturaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilAssinaturaEmpresaService.update).toHaveBeenCalledWith(
        expect.objectContaining(servicoContabilAssinaturaEmpresa),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilAssinaturaEmpresa>>();
      const servicoContabilAssinaturaEmpresa = { id: 123 };
      jest.spyOn(servicoContabilAssinaturaEmpresaFormService, 'getServicoContabilAssinaturaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilAssinaturaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilAssinaturaEmpresaFormService.getServicoContabilAssinaturaEmpresa).toHaveBeenCalled();
      expect(servicoContabilAssinaturaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilAssinaturaEmpresa>>();
      const servicoContabilAssinaturaEmpresa = { id: 123 };
      jest.spyOn(servicoContabilAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilAssinaturaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
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
