import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ImpostoEmpresaModeloDetailComponent } from './imposto-empresa-modelo-detail.component';

describe('ImpostoEmpresaModelo Management Detail Component', () => {
  let comp: ImpostoEmpresaModeloDetailComponent;
  let fixture: ComponentFixture<ImpostoEmpresaModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpostoEmpresaModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ImpostoEmpresaModeloDetailComponent,
              resolve: { impostoEmpresaModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ImpostoEmpresaModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpostoEmpresaModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load impostoEmpresaModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ImpostoEmpresaModeloDetailComponent);

      // THEN
      expect(instance.impostoEmpresaModelo()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
