import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';
import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaFormService } from './usuario-empresa-form.service';

import { UsuarioEmpresaUpdateComponent } from './usuario-empresa-update.component';

describe('UsuarioEmpresa Management Update Component', () => {
  let comp: UsuarioEmpresaUpdateComponent;
  let fixture: ComponentFixture<UsuarioEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioEmpresaFormService: UsuarioEmpresaFormService;
  let usuarioEmpresaService: UsuarioEmpresaService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioEmpresaUpdateComponent],
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
      .overrideTemplate(UsuarioEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioEmpresaFormService = TestBed.inject(UsuarioEmpresaFormService);
    usuarioEmpresaService = TestBed.inject(UsuarioEmpresaService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const usuarioEmpresa: IUsuarioEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 32410 };
      usuarioEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 29251 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuarioEmpresa: IUsuarioEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 4069 };
      usuarioEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.usuarioEmpresa).toEqual(usuarioEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaFormService, 'getUsuarioEmpresa').mockReturnValue(usuarioEmpresa);
      jest.spyOn(usuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(usuarioEmpresaFormService.getUsuarioEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaFormService, 'getUsuarioEmpresa').mockReturnValue({ id: null });
      jest.spyOn(usuarioEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioEmpresa }));
      saveSubject.complete();

      // THEN
      expect(usuarioEmpresaFormService.getUsuarioEmpresa).toHaveBeenCalled();
      expect(usuarioEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioEmpresa>>();
      const usuarioEmpresa = { id: 123 };
      jest.spyOn(usuarioEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioEmpresaService.update).toHaveBeenCalled();
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
