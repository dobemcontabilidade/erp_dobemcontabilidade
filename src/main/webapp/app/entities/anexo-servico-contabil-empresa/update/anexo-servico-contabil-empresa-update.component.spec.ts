import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from 'app/entities/servico-contabil-assinatura-empresa/service/servico-contabil-assinatura-empresa.service';
import { IAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';
import { AnexoServicoContabilEmpresaService } from '../service/anexo-servico-contabil-empresa.service';
import { AnexoServicoContabilEmpresaFormService } from './anexo-servico-contabil-empresa-form.service';

import { AnexoServicoContabilEmpresaUpdateComponent } from './anexo-servico-contabil-empresa-update.component';

describe('AnexoServicoContabilEmpresa Management Update Component', () => {
  let comp: AnexoServicoContabilEmpresaUpdateComponent;
  let fixture: ComponentFixture<AnexoServicoContabilEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoServicoContabilEmpresaFormService: AnexoServicoContabilEmpresaFormService;
  let anexoServicoContabilEmpresaService: AnexoServicoContabilEmpresaService;
  let anexoRequeridoService: AnexoRequeridoService;
  let servicoContabilAssinaturaEmpresaService: ServicoContabilAssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoServicoContabilEmpresaUpdateComponent],
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
      .overrideTemplate(AnexoServicoContabilEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoServicoContabilEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoServicoContabilEmpresaFormService = TestBed.inject(AnexoServicoContabilEmpresaFormService);
    anexoServicoContabilEmpresaService = TestBed.inject(AnexoServicoContabilEmpresaService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);
    servicoContabilAssinaturaEmpresaService = TestBed.inject(ServicoContabilAssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 17089 };
      anexoServicoContabilEmpresa.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 16330 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoServicoContabilEmpresa });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ServicoContabilAssinaturaEmpresa query and add missing value', () => {
      const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = { id: 456 };
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 3061 };
      anexoServicoContabilEmpresa.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;

      const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [{ id: 22824 }];
      jest
        .spyOn(servicoContabilAssinaturaEmpresaService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: servicoContabilAssinaturaEmpresaCollection })));
      const additionalServicoContabilAssinaturaEmpresas = [servicoContabilAssinaturaEmpresa];
      const expectedCollection: IServicoContabilAssinaturaEmpresa[] = [
        ...additionalServicoContabilAssinaturaEmpresas,
        ...servicoContabilAssinaturaEmpresaCollection,
      ];
      jest
        .spyOn(servicoContabilAssinaturaEmpresaService, 'addServicoContabilAssinaturaEmpresaToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoServicoContabilEmpresa });
      comp.ngOnInit();

      expect(servicoContabilAssinaturaEmpresaService.query).toHaveBeenCalled();
      expect(servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilAssinaturaEmpresaCollection,
        ...additionalServicoContabilAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.servicoContabilAssinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 5292 };
      anexoServicoContabilEmpresa.anexoRequerido = anexoRequerido;
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 5974 };
      anexoServicoContabilEmpresa.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;

      activatedRoute.data = of({ anexoServicoContabilEmpresa });
      comp.ngOnInit();

      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.servicoContabilAssinaturaEmpresasSharedCollection).toContain(servicoContabilAssinaturaEmpresa);
      expect(comp.anexoServicoContabilEmpresa).toEqual(anexoServicoContabilEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoServicoContabilEmpresa>>();
      const anexoServicoContabilEmpresa = { id: 123 };
      jest.spyOn(anexoServicoContabilEmpresaFormService, 'getAnexoServicoContabilEmpresa').mockReturnValue(anexoServicoContabilEmpresa);
      jest.spyOn(anexoServicoContabilEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoServicoContabilEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoServicoContabilEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoServicoContabilEmpresaFormService.getAnexoServicoContabilEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoServicoContabilEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(anexoServicoContabilEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoServicoContabilEmpresa>>();
      const anexoServicoContabilEmpresa = { id: 123 };
      jest.spyOn(anexoServicoContabilEmpresaFormService, 'getAnexoServicoContabilEmpresa').mockReturnValue({ id: null });
      jest.spyOn(anexoServicoContabilEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoServicoContabilEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoServicoContabilEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoServicoContabilEmpresaFormService.getAnexoServicoContabilEmpresa).toHaveBeenCalled();
      expect(anexoServicoContabilEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoServicoContabilEmpresa>>();
      const anexoServicoContabilEmpresa = { id: 123 };
      jest.spyOn(anexoServicoContabilEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoServicoContabilEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoServicoContabilEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServicoContabilAssinaturaEmpresa', () => {
      it('Should forward to servicoContabilAssinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilAssinaturaEmpresaService, 'compareServicoContabilAssinaturaEmpresa');
        comp.compareServicoContabilAssinaturaEmpresa(entity, entity2);
        expect(servicoContabilAssinaturaEmpresaService.compareServicoContabilAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
