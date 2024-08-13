import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CobrancaEmpresaDetailComponent } from './cobranca-empresa-detail.component';

describe('CobrancaEmpresa Management Detail Component', () => {
  let comp: CobrancaEmpresaDetailComponent;
  let fixture: ComponentFixture<CobrancaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CobrancaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CobrancaEmpresaDetailComponent,
              resolve: { cobrancaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CobrancaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CobrancaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cobrancaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CobrancaEmpresaDetailComponent);

      // THEN
      expect(instance.cobrancaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
