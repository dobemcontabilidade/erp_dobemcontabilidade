import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { GrupoAcessoEmpresaService } from '../service/grupo-acesso-empresa.service';
import { IGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaFormService } from './grupo-acesso-empresa-form.service';

import { GrupoAcessoEmpresaUpdateComponent } from './grupo-acesso-empresa-update.component';

describe('GrupoAcessoEmpresa Management Update Component', () => {
  let comp: GrupoAcessoEmpresaUpdateComponent;
  let fixture: ComponentFixture<GrupoAcessoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoAcessoEmpresaFormService: GrupoAcessoEmpresaFormService;
  let grupoAcessoEmpresaService: GrupoAcessoEmpresaService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoEmpresaUpdateComponent],
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
      .overrideTemplate(GrupoAcessoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoAcessoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoAcessoEmpresaFormService = TestBed.inject(GrupoAcessoEmpresaFormService);
    grupoAcessoEmpresaService = TestBed.inject(GrupoAcessoEmpresaService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 20085 };
      grupoAcessoEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 12832 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 29666 };
      grupoAcessoEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      activatedRoute.data = of({ grupoAcessoEmpresa });
      comp.ngOnInit();

      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.grupoAcessoEmpresa).toEqual(grupoAcessoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresa>>();
      const grupoAcessoEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoEmpresaFormService, 'getGrupoAcessoEmpresa').mockReturnValue(grupoAcessoEmpresa);
      jest.spyOn(grupoAcessoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoEmpresaFormService.getGrupoAcessoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoAcessoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(grupoAcessoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresa>>();
      const grupoAcessoEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoEmpresaFormService, 'getGrupoAcessoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(grupoAcessoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoEmpresaFormService.getGrupoAcessoEmpresa).toHaveBeenCalled();
      expect(grupoAcessoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoEmpresa>>();
      const grupoAcessoEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoAcessoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
