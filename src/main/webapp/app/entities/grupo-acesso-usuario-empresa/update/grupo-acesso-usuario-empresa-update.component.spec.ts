import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from 'app/entities/grupo-acesso-empresa/service/grupo-acesso-empresa.service';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { UsuarioEmpresaService } from 'app/entities/usuario-empresa/service/usuario-empresa.service';
import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import { GrupoAcessoUsuarioEmpresaService } from '../service/grupo-acesso-usuario-empresa.service';
import { GrupoAcessoUsuarioEmpresaFormService } from './grupo-acesso-usuario-empresa-form.service';

import { GrupoAcessoUsuarioEmpresaUpdateComponent } from './grupo-acesso-usuario-empresa-update.component';

describe('GrupoAcessoUsuarioEmpresa Management Update Component', () => {
  let comp: GrupoAcessoUsuarioEmpresaUpdateComponent;
  let fixture: ComponentFixture<GrupoAcessoUsuarioEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoAcessoUsuarioEmpresaFormService: GrupoAcessoUsuarioEmpresaFormService;
  let grupoAcessoUsuarioEmpresaService: GrupoAcessoUsuarioEmpresaService;
  let grupoAcessoEmpresaService: GrupoAcessoEmpresaService;
  let usuarioEmpresaService: UsuarioEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoUsuarioEmpresaUpdateComponent],
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
      .overrideTemplate(GrupoAcessoUsuarioEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoAcessoUsuarioEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoAcessoUsuarioEmpresaFormService = TestBed.inject(GrupoAcessoUsuarioEmpresaFormService);
    grupoAcessoUsuarioEmpresaService = TestBed.inject(GrupoAcessoUsuarioEmpresaService);
    grupoAcessoEmpresaService = TestBed.inject(GrupoAcessoEmpresaService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GrupoAcessoEmpresa query and add missing value', () => {
      const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = { id: 456 };
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 24245 };
      grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa = grupoAcessoEmpresa;

      const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [{ id: 22192 }];
      jest.spyOn(grupoAcessoEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoAcessoEmpresaCollection })));
      const additionalGrupoAcessoEmpresas = [grupoAcessoEmpresa];
      const expectedCollection: IGrupoAcessoEmpresa[] = [...additionalGrupoAcessoEmpresas, ...grupoAcessoEmpresaCollection];
      jest.spyOn(grupoAcessoEmpresaService, 'addGrupoAcessoEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa });
      comp.ngOnInit();

      expect(grupoAcessoEmpresaService.query).toHaveBeenCalled();
      expect(grupoAcessoEmpresaService.addGrupoAcessoEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        grupoAcessoEmpresaCollection,
        ...additionalGrupoAcessoEmpresas.map(expect.objectContaining),
      );
      expect(comp.grupoAcessoEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UsuarioEmpresa query and add missing value', () => {
      const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = { id: 456 };
      const usuarioEmpresa: IUsuarioEmpresa = { id: 31409 };
      grupoAcessoUsuarioEmpresa.usuarioEmpresa = usuarioEmpresa;

      const usuarioEmpresaCollection: IUsuarioEmpresa[] = [{ id: 19072 }];
      jest.spyOn(usuarioEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioEmpresaCollection })));
      const additionalUsuarioEmpresas = [usuarioEmpresa];
      const expectedCollection: IUsuarioEmpresa[] = [...additionalUsuarioEmpresas, ...usuarioEmpresaCollection];
      jest.spyOn(usuarioEmpresaService, 'addUsuarioEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa });
      comp.ngOnInit();

      expect(usuarioEmpresaService.query).toHaveBeenCalled();
      expect(usuarioEmpresaService.addUsuarioEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioEmpresaCollection,
        ...additionalUsuarioEmpresas.map(expect.objectContaining),
      );
      expect(comp.usuarioEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = { id: 456 };
      const grupoAcessoEmpresa: IGrupoAcessoEmpresa = { id: 17964 };
      grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa = grupoAcessoEmpresa;
      const usuarioEmpresa: IUsuarioEmpresa = { id: 15565 };
      grupoAcessoUsuarioEmpresa.usuarioEmpresa = usuarioEmpresa;

      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa });
      comp.ngOnInit();

      expect(comp.grupoAcessoEmpresasSharedCollection).toContain(grupoAcessoEmpresa);
      expect(comp.usuarioEmpresasSharedCollection).toContain(usuarioEmpresa);
      expect(comp.grupoAcessoUsuarioEmpresa).toEqual(grupoAcessoUsuarioEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioEmpresa>>();
      const grupoAcessoUsuarioEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioEmpresaFormService, 'getGrupoAcessoUsuarioEmpresa').mockReturnValue(grupoAcessoUsuarioEmpresa);
      jest.spyOn(grupoAcessoUsuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoUsuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoUsuarioEmpresaFormService.getGrupoAcessoUsuarioEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoAcessoUsuarioEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(grupoAcessoUsuarioEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioEmpresa>>();
      const grupoAcessoUsuarioEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioEmpresaFormService, 'getGrupoAcessoUsuarioEmpresa').mockReturnValue({ id: null });
      jest.spyOn(grupoAcessoUsuarioEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoUsuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoUsuarioEmpresaFormService.getGrupoAcessoUsuarioEmpresa).toHaveBeenCalled();
      expect(grupoAcessoUsuarioEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoUsuarioEmpresa>>();
      const grupoAcessoUsuarioEmpresa = { id: 123 };
      jest.spyOn(grupoAcessoUsuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoUsuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoAcessoUsuarioEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGrupoAcessoEmpresa', () => {
      it('Should forward to grupoAcessoEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoAcessoEmpresaService, 'compareGrupoAcessoEmpresa');
        comp.compareGrupoAcessoEmpresa(entity, entity2);
        expect(grupoAcessoEmpresaService.compareGrupoAcessoEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUsuarioEmpresa', () => {
      it('Should forward to usuarioEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioEmpresaService, 'compareUsuarioEmpresa');
        comp.compareUsuarioEmpresa(entity, entity2);
        expect(usuarioEmpresaService.compareUsuarioEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
